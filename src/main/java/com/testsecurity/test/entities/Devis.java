package com.testsecurity.test.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Devis  implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer idDevis;
    LocalDate dateDevis;

    @ManyToOne
    Prospect prospect ;

    @OneToMany
    Set<Beneficiaire> beneficiaires ;
}
