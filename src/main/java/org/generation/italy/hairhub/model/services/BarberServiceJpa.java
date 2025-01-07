package org.generation.italy.hairhub.model.services;

import org.generation.italy.hairhub.model.entities.Barber;
import org.generation.italy.hairhub.model.repositories.BarberRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarberServiceJpa implements BarberService{
    private BarberRepositoryJpa barberRepo;

    @Autowired
    public BarberServiceJpa(BarberRepositoryJpa barberRepo) {
        this.barberRepo = barberRepo;
    }


}
