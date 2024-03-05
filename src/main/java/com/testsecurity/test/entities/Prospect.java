package com.testsecurity.test.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Prospect  implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer idProspect;
    String nom;
    String prenom;
    String email;

    @OneToOne (mappedBy = "prospect")
    Compte compte;

    @OneToMany
    Set<Devis> Devis ;


}
