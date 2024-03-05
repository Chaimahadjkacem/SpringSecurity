package com.testsecurity.test.services.auth;

import com.testsecurity.test.entities.Compte;
import com.testsecurity.test.entities.Model.auth.ExtendedCompte;
import com.testsecurity.test.services.CompteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationUserDetailsService  implements UserDetailsService {

    private final CompteService compteService ;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Compte compte = compteService.findByEmail(email);
        return (UserDetails) new ExtendedCompte(compte.getEmail() ,compte.getMdp());
    }
}
