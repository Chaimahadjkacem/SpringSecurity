package com.testsecurity.test.repositories;

import com.testsecurity.test.entities.Compte;
import com.testsecurity.test.entities.Prospect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProspectRepository extends JpaRepository<Prospect, Integer> {

    // findByEmail !!
    Optional<Prospect> findProspectByEmail(String email);

}