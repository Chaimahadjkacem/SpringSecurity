package com.testsecurity.test.configurations;

import com.testsecurity.test.entities.Compte;
import com.testsecurity.test.repositories.CompteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final  CompteRepository compteRepository ;

    public Optional<Compte> getUserBySession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            return compteRepository.findCompteByEmail(username);
        } else {
            return null; // or throw an exception, depending on your requirements
        }
    }
}
