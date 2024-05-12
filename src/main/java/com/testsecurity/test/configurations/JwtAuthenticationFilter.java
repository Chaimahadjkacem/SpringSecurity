package com.testsecurity.test.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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

    private final UserDetailsService userDetailsService ;
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
        //and not authenticated yet because if is authenticated i don't need to perfom again all the chesks ans setting or updating the security context
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //get user Details from the DB
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            //if the token is valid we need to update the security context + send request to dispatcher Servlet
            if (jwtService.isTokenValid(jwt , userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails , null , userDetails.getAuthorities()
                );
                //reforce this authentication token with the details of our request
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                //Update the authentication token
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request , response);

    }
}
