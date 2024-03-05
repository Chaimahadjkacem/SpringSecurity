package com.testsecurity.test.services;

import com.testsecurity.test.entities.Compte;
import com.testsecurity.test.entities.Prospect;
import com.testsecurity.test.repositories.CompteRepository;
import com.testsecurity.test.repositories.ProspectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProspectService implements IProspectService {

    public final ProspectRepository prospectRepository ;
    public final CompteRepository compteRepository ;


    ////ENAAAAAAA
    @Override
    public void addProspectAndCompte(Prospect prospect ) {
        prospectRepository.save(prospect);
        Compte c = new Compte();
        c.setEmail(prospect.getEmail());

        String rawPassword = prospect.getNom() + prospect.getPrenom();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(rawPassword);

        c.setMdp(encryptedPassword);

        compteRepository.save(c);
    }

}
