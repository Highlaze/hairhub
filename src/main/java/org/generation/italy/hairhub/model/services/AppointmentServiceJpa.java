package org.generation.italy.hairhub.model.services;

import org.generation.italy.hairhub.dto.AppointmentDto;
import org.generation.italy.hairhub.model.entities.Appointment;
import org.generation.italy.hairhub.model.entities.Barber;
import org.generation.italy.hairhub.model.entities.Treatment;
import org.generation.italy.hairhub.model.entities.User;
import org.generation.italy.hairhub.model.AppointmentWithPrices;
import org.generation.italy.hairhub.model.TreatmentWithPrice;
import org.generation.italy.hairhub.model.entities.*;
import org.generation.italy.hairhub.model.exceptions.EntityNotFoundException;
import org.generation.italy.hairhub.model.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AppointmentServiceJpa implements AppointmentService {
    private AppointmentRepositoryJpa appRepo;
    private BarberRepositoryJpa barberRepo;
    private TreatmentRepositoryJpa treatRepo;
    private UserRepositoryJpa userRepo;
    private SalonTreatmentRepositoryJpa salonTreatRepo;
    private SalonRepositoryJpa salonRepo;

    @Autowired
    public AppointmentServiceJpa(AppointmentRepositoryJpa appRepo, BarberRepositoryJpa barberRepo, TreatmentRepositoryJpa treatRepo, UserRepositoryJpa userRepo, SalonTreatmentRepositoryJpa salonTreatRepo) {
        this.appRepo = appRepo;
        this.barberRepo = barberRepo;
        this.treatRepo = treatRepo;
        this.userRepo = userRepo;
        this.salonTreatRepo = salonTreatRepo;
    }

    @Override
    public Optional<Appointment> update(long id) {
        Optional<Appointment> oa = appRepo.findById(id);
//        if (oa.isPresent()) {
//            Appointment appointment = oa.get();
//            Period period = Period.between(appointment.getDate(), LocalDate.now()); //prende il lasso di tempo tra l'ora dell'appuntamento e l'ora di adesso
//            if (period.getDays() > 1) { //period.getDays() diventa un numero, ovvero i giorni che mancano tra oggi e il giorno dellappuntamento
//                Appointment updateAppointment = appRepo.save(appointment);
//                return Optional.of(updateAppointment);
//            }
        if (oa.isPresent()) {
            Appointment appointment = oa.get();
            appointment.setStatus("Cancelled");
            Appointment updateAppointment = appRepo.save(appointment);
            return Optional.of(updateAppointment);
        }
        return Optional.empty();
    }

    @Override
    public AppointmentWithPrices create(Appointment app, long barberId, List<Long> treatmentIds, long userId) throws EntityNotFoundException{
        Optional<Barber> ob = barberRepo.findById(barberId);//trova tutte le prenotazioni con gli id specificati
        Optional<User> ou = userRepo.findById(userId);
        if (ob.isEmpty() || ou.isEmpty()) {
            throw new EntityNotFoundException("Entità non trovata", ob.isEmpty() ? Barber.class.getSimpleName() : User.class.getSimpleName());
        }
        List<Treatment> treatments = treatRepo.findAllById(treatmentIds);
        if(treatments.size() != treatmentIds.size()){
            throw new EntityNotFoundException("Treatment non trovati", null);
        }

        app.setTreatments(treatments);
        app.setBarber(ob.get());
        //.get() va a prendere il contenuto all'interno dell'Optional, perché non posso passare all'entità Appointment un Optional, ma solo il suo contenuto
        app.setUser(ou.get());
        app.setStatus("Confirmed");
        appRepo.save(app); //salva l'appuntamento sul db tramite il repository(il repository è quello che comunica col db)

        List<TreatmentWithPrice> treatmentsWithPrice = new ArrayList<>();
        for (Treatment treatment : treatments) {
            double price = salonTreatRepo.getPriceBySalonIdAndTreatmentId(app.getBarber().getSalon().getId(), treatment.getId());
            treatmentsWithPrice.add(new TreatmentWithPrice(treatment, price));
        }

        return new AppointmentWithPrices(
                app.getId(),
                app.getUser(),
                app.getBarber(),
                treatmentsWithPrice,
                app.getDate(),
                app.getStartTime(),
                app.getEndTime(),
                app.getStatus()
        );
    }

    @Override
    public List<LocalTime> getAvailableTimesForBarber(long barberId, LocalDate date,int numberOfTreatments) throws EntityNotFoundException {
        List<Appointment> appointments = appRepo.findByBarberIdAndDate(barberId,date);
        Barber barber = barberRepo.findById(barberId).orElseThrow(()-> new EntityNotFoundException("entity not found",Barber.class.getName()));
        Salon salon = barber.getSalon();
        LocalTime openingTime = salon.getOpeningTime();
        LocalTime closingTime = salon.getClosingTime();
        List<LocalTime> availableTimes = generateAvailableTimes(openingTime,closingTime,appointments,numberOfTreatments,date);
        return availableTimes;
    }



    @Override
    public List<LocalDate> getAvailableDatesForBarber(long barberId,int bookingMonths, int numberOfTreatments) throws EntityNotFoundException {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusMonths(bookingMonths);
        Barber barber = barberRepo.findById(barberId).orElseThrow(()-> new EntityNotFoundException("entity not found",Barber.class.getName()));
        Salon salon = barber.getSalon();
        int closingDay = salon.getClosingDay();
        List<Appointment> appointments = appRepo.findByBarberIdAndDateBetween(barberId,today,endDate);
        Map<LocalDate,List<Appointment>> appointmentsByDate = new HashMap<>();
        for(Appointment appointment : appointments){
            LocalDate date = appointment.getDate();
            if(!appointmentsByDate.containsKey(date)){
                appointmentsByDate.put(date, new ArrayList<>());
            }
            appointmentsByDate.get(date).add(appointment);
        }
        List<LocalDate> availableDates = new ArrayList<>();
        for(LocalDate date = today; !date.isAfter(endDate);date = date.plusDays(1)){
            if(date.getDayOfWeek().getValue() == closingDay){
                continue;
            }
            List<Appointment> dailyAppointments = appointmentsByDate.getOrDefault(date,new ArrayList<>());
            if(!dailyAppointments.isEmpty()) {
                List<LocalTime> availableTimes = getAvailableTimesForBarber(barberId,date,numberOfTreatments);
                if(availableTimes.isEmpty()) {
                    continue;
                }
            }
            availableDates.add(date);
        }
            return availableDates;
        }

    @Override
    public List<LocalTime> generateAvailableTimes(LocalTime openingTime, LocalTime closingTime, List<Appointment> appointments, int numberOfTreatments, LocalDate selectedDate) {
        Duration duration = Duration.ofMinutes(numberOfTreatments * 30);
       List<LocalTime> availableTimes = new ArrayList<>();
       LocalTime slot = openingTime;
       LocalTime now = LocalTime.now();

        if (selectedDate.equals(LocalDate.now()) && now.isAfter(openingTime)) {
            slot = now.withMinute((now.getMinute() / 30) * 30).plusMinutes(30);
            if (slot.isBefore(now)) {
                slot = slot.plusMinutes(30);  // Se siamo in un minuto esatto come 11:30, dobbiamo passare al successivo
            }
        }

       while (!slot.plus(duration).isAfter(closingTime)){
           boolean isSlotTaken= false;
           for(Appointment appointment: appointments){

               if(slot.isBefore(appointment.getEndTime()) && slot.plus(duration).isAfter(appointment.getStartTime())){
                   isSlotTaken = true;
                   break;
               }
           }
           if (!isSlotTaken){
               availableTimes.add(slot);
           }
           slot = slot.plusMinutes(30);
       }
       return availableTimes;
    }

    @Override
    public List<AppointmentWithPrices> getFutureAppointmentsByUserId(long userId) {
        List<Appointment> appointments = appRepo.findFutureAppointmentsByUserId(userId, LocalDate.now());
        List<TreatmentWithPrice> treatmentsPrice = new ArrayList<>();
        List<AppointmentWithPrices> appointmentsPrice = new ArrayList<>();
        for(Appointment appointment : appointments) {
            List<Treatment> treatments = appointment.getTreatments();
            for (Treatment t : treatments) {
                double price = salonTreatRepo.getPriceBySalonIdAndTreatmentId(appointment.getBarber().getSalon().getId(), t.getId());
                treatmentsPrice.add(new TreatmentWithPrice(t, price));
            }
            appointmentsPrice.add(new AppointmentWithPrices(
                    appointment.getId(),
                    appointment.getUser(),
                    appointment.getBarber(),
                    treatmentsPrice,
                    appointment.getDate(),
                    appointment.getStartTime(),
                    appointment.getEndTime(),
                    appointment.getStatus()
            ));
        }
        return appointmentsPrice;
    }

    @Override
    public List<AppointmentWithPrices> getPastAppointmentsByUserId(long userId) {
        List<Appointment> appointments = appRepo.findPastAppointmentsByUserId(userId, LocalDate.now());
        List<TreatmentWithPrice> treatmentsPrice = new ArrayList<>();
        List<AppointmentWithPrices> appointmentsPrice = new ArrayList<>();
        for(Appointment appointment : appointments) {
            List<Treatment> treatments = appointment.getTreatments();
            for (Treatment t : treatments) {
                double price = salonTreatRepo.getPriceBySalonIdAndTreatmentId(appointment.getBarber().getSalon().getId(), t.getId());
                treatmentsPrice.add(new TreatmentWithPrice(t, price));
            }
            appointmentsPrice.add(new AppointmentWithPrices(
                    appointment.getId(),
                    appointment.getUser(),
                    appointment.getBarber(),
                    treatmentsPrice,
                    appointment.getDate(),
                    appointment.getStartTime(),
                    appointment.getEndTime(),
                    appointment.getStatus()
            ));
        }
        return appointmentsPrice;
    }
}