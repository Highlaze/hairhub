package org.generation.italy.hairhub.model.repositories;

import org.generation.italy.hairhub.model.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepositoryJpa extends JpaRepository <Appointment, Long> {
List<Appointment> findByBarberIdAndDateBetween(long barberId, LocalDate today, LocalDate endDate);
List<Appointment> findByBarberIdAndDate(long barberId, LocalDate date);



}
