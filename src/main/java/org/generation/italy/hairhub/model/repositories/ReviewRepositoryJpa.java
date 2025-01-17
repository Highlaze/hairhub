package org.generation.italy.hairhub.model.repositories;

import org.generation.italy.hairhub.model.entities.Review;
import org.generation.italy.hairhub.model.entities.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepositoryJpa extends JpaRepository<Review, Long> {
    @Query("""
                SELECT r
                FROM Review r
                JOIN r.appointment a
                JOIN a.barber b
                JOIN b.salon s
                WHERE s.id = :salonId
            """)
    List<Review> findReviewsBySalonId(@Param("salonId") long salonId);


    Optional<Review> findByAppointmentId(long appointmentId);
}