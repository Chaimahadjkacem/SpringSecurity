package com.testsecurity.test.services;

import com.testsecurity.test.entities.Compte;
import com.testsecurity.test.entities.Prospect;
import com.testsecurity.test.repositories.CompteRepository;
import com.testsecurity.test.repositories.ProspectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CompteService implements ICompteService{

    @Autowired
     CompteRepository compteRepository ;


    PasswordEncoder passwordEncoder;

    @Autowired
    public CompteService(){
        this.passwordEncoder=new BCryptPasswordEncoder();
    }


    public Compte findByEmail(String email) {
        return compteRepository.findCompteByEmail(email).orElseThrow(()->new EntityNotFoundException((
                "Prospect not found with email =" +email)
        ));
    }

    @Override
    public Compte CreateForReset(Compte c) {
        c.setMdp(this.passwordEncoder.encode(c.getMdp()));
        compteRepository.save(c);
        return c;
    }

    @Override
    public Compte findByResetToken(String resetToken) {
        return compteRepository.findByResetToken(resetToken);
    }

    @Override
    public Compte Create(Compte c) {
        if(validatePassword(c.getMdp())) {
            c.setMdp(this.passwordEncoder.encode(c.getMdp()));
            return compteRepository.save(c);
        }
        return new Compte();
    }

    public boolean validatePassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (isSpecialChar(c)) {
                hasSpecialChar = true;

            }
        }
        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }

    private boolean isSpecialChar(char c) {
        // Replace this with your desired set of special characters
        String specialChars = "!@#$%^&*()_+-=[]{}|;':\"<>,.?/\\";
        return specialChars.indexOf(c) != -1;
    }


    ////ENAAAAAAA

}
