package com.testsecurity.test.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private  final  JwtService jwtService ;
    //add @NotNull
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,@NotNull HttpServletResponse response,@NotNull FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization") ;
        //to implement this check that we did before (in JwtAuthFilter)
        final String jwt ;

        final String userEmail ;
        //the build token should be always start wth the keyword berear
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            //to pass the request and the response to the next filter
            filterChain.doFilter(request , response);
            return;
        }

        //extract the JWT token
        jwt = authorizationHeader.substring(7);

        //extract the useremail from JWT token So i need a class can manipulate this JWT token
        userEmail = jwtService.extractUsername(jwt);


    }
}
