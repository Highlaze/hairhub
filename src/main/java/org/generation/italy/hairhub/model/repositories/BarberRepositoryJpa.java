package org.generation.italy.hairhub.model.repositories;

import org.generation.italy.hairhub.model.entities.Barber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BarberRepositoryJpa extends JpaRepository<Barber, Long> {

}