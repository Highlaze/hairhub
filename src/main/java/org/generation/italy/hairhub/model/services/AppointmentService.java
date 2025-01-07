package org.generation.italy.hairhub.model.services;

import org.generation.italy.hairhub.dto.AppointmentDto;
import org.generation.italy.hairhub.model.AppointmentWithPrices;
import org.generation.italy.hairhub.model.entities.Appointment;
import org.generation.italy.hairhub.model.exceptions.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Optional<Appointment> update(long id);
    List<AppointmentWithPrices> getFutureAppointmentsByUserId(long userId);
    List<AppointmentWithPrices> getPastAppointmentsByUserId(long userId);
    AppointmentWithPrices create(Appointment app, long barberId, List<Long> treatmentsId, long userId) throws EntityNotFoundException;
    List<LocalDate> getAvailableDatesForBarber(long barberId, int bookingMonths, int numberOfTreatments) throws EntityNotFoundException;
    List<LocalTime> getAvailableTimesForBarber(long barberId, LocalDate date,int numberOfTreatments) throws EntityNotFoundException;
    List<LocalTime> generateAvailableTimes(LocalTime openingTime, LocalTime closingTime, List<Appointment> appointments, int numberOfTreatments);



}
