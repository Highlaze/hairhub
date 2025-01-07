package org.generation.italy.hairhub.model.repositories;

import org.generation.italy.hairhub.model.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepositoryJpa extends JpaRepository <Appointment, Long> {
    @Query("SELECT a FROM Appointment a WHERE a.user.id = :userId AND a.date >= :currentDate")
    List<Appointment> findFutureAppointmentsByUserId(@Param("userId") long userId, @Param("currentDate") LocalDate currentDate);

    @Query("SELECT a FROM Appointment a WHERE a.user.id = :userId AND a.date < :currentDate")
    List<Appointment> findPastAppointmentsByUserId(@Param("userId") long userId, @Param("currentDate") LocalDate currentDate);
List<Appointment> findByBarberIdAndDateBetween(long barberId, LocalDate today, LocalDate endDate);
List<Appointment> findByBarberIdAndDate(long barberId, LocalDate date);



}
