package org.generation.italy.hairhub.model.repositories;

import org.generation.italy.hairhub.model.entities.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreatmentRepositoryJpa extends JpaRepository<Treatment, Long> {

    List<Treatment> findAll();
}
