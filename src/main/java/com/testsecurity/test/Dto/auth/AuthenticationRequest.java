package com.testsecurity.test.Dto.auth;


import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    private  String login;
    private  String password;
}
