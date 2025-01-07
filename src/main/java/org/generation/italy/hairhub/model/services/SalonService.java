package org.generation.italy.hairhub.model.services;
import org.generation.italy.hairhub.model.TreatmentWithPrice;
import org.generation.italy.hairhub.model.entities.Barber;
import org.generation.italy.hairhub.model.entities.Salon;
import org.generation.italy.hairhub.model.entities.Treatment;

import java.util.List;
import java.util.Optional;

public interface SalonService {
    List<Salon> getTopSalons(Integer type, int num);
    List<Salon> getSalonsByNameOrAddress(String name);
    Optional<Salon> getSalonById(long id);
    List<Salon> getSalonsByTreatmentId(long treatmentId);
    List<TreatmentWithPrice> getTreatmentBySalon(long salonId);
    List<Barber> getAllBarbersBySalonId(long salonId);
}

