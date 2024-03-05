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
public class Beneficiaire implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer idBeneficiaire;
    String nom;
    String prenom;
    TypeBeneficiaire typeBeneficiaire ;


    @ManyToOne
    Devis devis ;

}
