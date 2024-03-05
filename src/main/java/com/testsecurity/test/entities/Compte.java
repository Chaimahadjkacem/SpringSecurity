package com.testsecurity.test.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Compte  implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer idCompte;
    String email;
    String mdp;
    LocalDate dateCreation ;
    EtatCompte etatCompte ;

    private String resetToken;
    private boolean TokenExpired;



    public Compte(String email, String mdp) {
    }


    @OneToOne
    Prospect prospect ;

}
