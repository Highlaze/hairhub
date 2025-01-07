package org.generation.italy.hairhub.model.repositories;

import org.generation.italy.hairhub.model.entities.Barber;
import org.generation.italy.hairhub.model.entities.Salon;
import org.generation.italy.hairhub.model.entities.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalonRepositoryJpa extends JpaRepository<Salon, Long> {
    @Query("""
                SELECT s
                FROM Review r
                JOIN r.appointment a
                JOIN a.barber b
                JOIN b.salon s
                WHERE (:type IS NULL OR EXISTS (
                    SELECT 1
                    FROM Treatment t
                    JOIN t.appointments ap
                    WHERE ap = a
                    )
                )
                GROUP BY s
                ORDER BY AVG(distinct r.rating) DESC
                LIMIT :num
            """)
    List<Salon> getSalonsByOptionalServiceType(@Param("type") Integer type, @Param("num") int num);


    // l'indirizzo non serve più???????????????????????????????????????
    @Query("""
                SELECT s
                FROM Salon s
                WHERE (:toSearch IS NULL
                    OR :toSearch = ''
                    OR LOWER(s.name) LIKE LOWER(CONCAT('%', :toSearch, '%'))
                    OR LOWER(s.city) LIKE LOWER(CONCAT('%', :toSearch, '%'))
                    OR LOWER(s.cap) LIKE LOWER(CONCAT('%', :toSearch, '%'))
                    OR LOWER(s.address) LIKE LOWER(CONCAT('%', :toSearch, '%')))
            """)
    List<Salon> searchByNameOrAddress(@Param("toSearch") String searchString);

    // cos'è??????????????????????????????????????????????????????????
    @Query("""
                SELECT s
                FROM Salon s
                WHERE :treatmentId = ANY (SELECT t.id FROM s.treatments t)
            """)
    List<Salon> findByTreatmentId(@Param("treatmentId") long treatmentId);

    @Query("""
            SELECT b
            FROM Barber b
            WHERE b.salon.id = :salonId
           """)
    List<Barber> findBarbersBySalonId(@Param("salonId") long salonId);




}