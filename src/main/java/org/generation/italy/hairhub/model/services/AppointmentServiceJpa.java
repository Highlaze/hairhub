package org.generation.italy.hairhub.model.services;

import org.generation.italy.hairhub.dto.AppointmentDto;
import org.generation.italy.hairhub.model.entities.Appointment;
import org.generation.italy.hairhub.model.entities.Barber;
import org.generation.italy.hairhub.model.entities.Treatment;
import org.generation.italy.hairhub.model.entities.User;
import org.generation.italy.hairhub.model.exceptions.EntityNotFoundException;
import org.generation.italy.hairhub.model.repositories.AppointmentRepositoryJpa;
import org.generation.italy.hairhub.model.repositories.BarberRepositoryJpa;
import org.generation.italy.hairhub.model.repositories.TreatmentRepositoryJpa;
import org.generation.italy.hairhub.model.repositories.UserRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceJpa implements AppointmentService {
    private AppointmentRepositoryJpa appRepo;
    private BarberRepositoryJpa barberRepo;
    private TreatmentRepositoryJpa treatRepo;
    private UserRepositoryJpa userRepo;

    @Autowired
    public AppointmentServiceJpa(AppointmentRepositoryJpa appRepo, BarberRepositoryJpa barberRepo, TreatmentRepositoryJpa treatRepo, UserRepositoryJpa userRepo) {
        this.appRepo = appRepo;
        this.barberRepo = barberRepo;
        this.treatRepo = treatRepo;
        this.userRepo = userRepo;
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
    public Appointment create(Appointment app, long barberId, long treatmentId, long userId) throws EntityNotFoundException{
        Optional<Barber> ob = barberRepo.findById(barberId);
        Optional<Treatment> ot = treatRepo.findById(treatmentId);
        Optional<User> ou = userRepo.findById(userId);
        if (ob.isEmpty() || ot.isEmpty() || ou.isEmpty()) {
            throw new EntityNotFoundException("Entità non trovata", ob.isEmpty() ? Barber.class.getSimpleName() : (ot.isEmpty() ? Treatment.class.getSimpleName() : User.class.getSimpleName()));
        }
        app.setBarber(ob.get());
        app.setTreatment(ot.get()); //.get() va a prendere il contenuto all'interno dell'Optional, perché non posso passare all'entità Appointment un Optional, ma solo il suo contenuto
        app.setUser(ou.get());
        app.setStatus("Confirmed");
        appRepo.save(app); //salva l'appuntamento sul db tramite il repository(il repository è quello che comunica col db)
        return app;
    }

    @Override
    public List<AppointmentDto> getFuturAppointmentByUserId(long userId, LocalDate CurrentDate) {
        return appRepo.findFutureAppointmentsByUserId(userId, LocalDate.now()).stream().map(AppointmentDto::fromAppointment).collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDto> getPastAppointmentByUserId(long userId, LocalDate CurrentDate) {
        return appRepo.findPastAppointmentsByUserId(userId, LocalDate.now()).stream().map(AppointmentDto::fromAppointment).collect(Collectors.toList());
    }
}