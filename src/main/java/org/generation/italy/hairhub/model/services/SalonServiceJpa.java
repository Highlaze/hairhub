package org.generation.italy.hairhub.model.services;

import org.generation.italy.hairhub.model.TreatmentWithPrice;
import org.generation.italy.hairhub.model.entities.Barber;
import org.generation.italy.hairhub.model.entities.Salon;
import org.generation.italy.hairhub.model.entities.Treatment;
import org.generation.italy.hairhub.model.repositories.SalonRepositoryJpa;
import org.generation.italy.hairhub.model.repositories.SalonTreatmentRepositoryJpa;
import org.generation.italy.hairhub.model.repositories.TreatmentRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SalonServiceJpa implements SalonService {
    SalonRepositoryJpa salonRepo;
    SalonTreatmentRepositoryJpa salonTreatRepo;
    TreatmentRepositoryJpa treatRepo;

    @Autowired
    public SalonServiceJpa(SalonRepositoryJpa salonRepo, SalonTreatmentRepositoryJpa salonTreatRepo, TreatmentRepositoryJpa treatRepo) {
        this.salonRepo = salonRepo;
        this.salonTreatRepo = salonTreatRepo;
        this.treatRepo = treatRepo;
    }

    @Override
    public List<Salon> getTopSalons(Integer type, int num) {
        List<Salon> salons=  salonRepo.getSalonsByOptionalServiceType(type,num);
        System.out.println("Found Salons: " + salons.size());  // Aggiungi questo per il debug
        return salons;
    }


    @Override
    public List<Salon> getSalonsByNameOrAddress(String name) {
        return salonRepo.searchByNameOrAddress(name);
    }

    @Override
    public Optional<Salon> getSalonById(long id) {
        return salonRepo.findById(id);
    }

    @Override
    public List<Salon> getSalonsByTreatmentId(long treatmentId) {
        return salonRepo.findByTreatmentId(treatmentId);
    }

    @Override
    public List<TreatmentWithPrice> getTreatmentBySalon(long salonId) {
        List<Treatment> allTreatment = treatRepo.findAll();
        List<TreatmentWithPrice> treatsWPrice = new ArrayList<>();
        for (Treatment treatment : allTreatment) {
            double price = salonTreatRepo.getPriceBySalonIdAndTreatmentId(salonId, treatment.getId());

            TreatmentWithPrice treatWPrice = new TreatmentWithPrice(treatment, price);
            treatsWPrice.add(treatWPrice);
        }
        return treatsWPrice;
    }
    @Override
    public List<Barber> getAllBarbersBySalonId(long salonId) {
        return salonRepo.findBarbersBySalonId(salonId);
    }
}
