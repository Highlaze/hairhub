package org.generation.italy.hairhub.model.repositories;

import org.generation.italy.hairhub.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryJpa extends JpaRepository<User, Long> {
    Optional<User> findByMail(String mail);
}
