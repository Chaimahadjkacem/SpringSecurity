package com.testsecurity.test.repositories;

import com.testsecurity.test.entities.Compte;
import com.testsecurity.test.entities.Prospect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte, Integer> {
    Optional<Compte> findCompteByEmail(String email);

    Compte findByResetToken(String resetToken);

}