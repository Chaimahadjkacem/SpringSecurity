package com.testsecurity.test.configurations;

import com.testsecurity.test.Dto.auth.AuthenticationRequest;
import com.testsecurity.test.Dto.auth.AuthenticationResponse;
import com.testsecurity.test.Dto.auth.RegisterRequest;
import com.testsecurity.test.entities.Prospect;
import com.testsecurity.test.repositories.ProspectRepository;
import lombok.RequiredArgsConstructor;

import lombok.var;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final ProspectRepository repository ;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService ;

    private final AuthenticationManager authenticationManager;



    //register a user + return a generated token
    public AuthenticationResponse register(RegisterRequest request){
        var prosp = Prospect.builder()
                .nom(request.getLastname())
                .prenom(request.getFirstname())
                .email(request.getEmail())
                //We need to encode our password bed=fore we save it to the DB
                .pwd(passwordEncoder.encode(request.getPassword()))
                .build();
        repository.save(prosp);

        //to return the response with auth token
        var jwtToken = jwtService.generateToken(prosp);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        //login and password are correct
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()

                )
        );
        var prosp = repository.findProspectByEmail(request.getLogin()).orElse(null);
        var jwtToken = jwtService.generateToken(prosp);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }
}
