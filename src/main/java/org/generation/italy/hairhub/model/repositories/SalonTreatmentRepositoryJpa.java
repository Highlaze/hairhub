package org.generation.italy.hairhub.model.repositories;

import org.generation.italy.hairhub.model.entities.SalonTreatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface SalonTreatmentRepositoryJpa extends JpaRepository<SalonTreatment, Long> {
    @Query
            ("""
            select st.price
            from SalonTreatment st
            where st.salon.id = :salonId
            and st.treatment.id = :treatmentId
            """)
    double getPriceBySalonIdAndTreatmentId(@Param("salonId") long salonId, @Param("treatmentId") long treatmentId);

}
