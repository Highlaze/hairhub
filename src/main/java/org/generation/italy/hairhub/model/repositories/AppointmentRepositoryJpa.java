package org.generation.italy.hairhub.model.repositories;

import org.generation.italy.hairhub.model.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepositoryJpa extends JpaRepository <Appointment, Long> {
    @Query("""
            SELECT a
            FROM Appointment a
            WHERE a.user.id = :userId AND a.date >= :CurrentDate
            """)
    List<Appointment> findFutureAppointmentsByUserId(@Param("userId") long userId,@Param("CurrentDate") LocalDate date);
    @Query("""
            SELECT a
            FROM Appointment a
            WHERE a.user.id = :userId AND a.date < :CurrentDate
            """)
    List<Appointment> findPastAppointmentsByUserId(@Param("userId") long userId,@Param("CurrentDate") LocalDate CurrentDate);
}
