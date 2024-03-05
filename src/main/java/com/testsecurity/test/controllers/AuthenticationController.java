package com.testsecurity.test.controllers;


import com.testsecurity.test.Dto.auth.AuthenticationRequest;
import com.testsecurity.test.Dto.auth.AuthenticationResponse;
import com.testsecurity.test.configurations.JWT;
import com.testsecurity.test.configurations.SpringSecurityConfiguration;
import com.testsecurity.test.repositories.CompteRepository;
import com.testsecurity.test.services.auth.ApplicationUserDetailsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApplicationUserDetailsService userDetailsService;

    @Autowired
    private JWT jwt;

    public final CompteRepository compteRepository;


    @PostMapping("auth")
    @SecurityRequirements
    public AuthenticationResponse authenticate(@NotNull @RequestBody AuthenticationRequest Request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        Request.getLogin(),
                        Request.getPassword()
                )
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(Request.getLogin());

        // Vérifier si le mot de passe fourni correspond au mot de passe enregistré pour l'utilisateur
        if (userDetails != null && SpringSecurityConfiguration.matchPassword(Request.getPassword(), userDetails.getPassword())) {

            final String JWTT = jwt.generateToken(userDetails);
         /*   HttpHeaders headers=new HttpHeaders();
            headers.set("Authorization","Bearer" + JWTT);*/
            return new AuthenticationResponse(JWTT);
        } else {
            // Le mot de passe est incorrect, retourner une erreur d'authentification
            throw new BadCredentialsException("Invalid username or password");
        }


    }

}
