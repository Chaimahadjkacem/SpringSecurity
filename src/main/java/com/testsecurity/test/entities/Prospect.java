package com.testsecurity.test.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Prospect  implements Serializable , UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer idProspect;
    String nom;
    String prenom;

    //normalement hethom f compte !! + email f contact !!
    String email;
    String pwd;

    @OneToOne (mappedBy = "prospect")
    Compte compte;

    @OneToMany
    Set<Devis> Devis ;


//return a list of roles
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return pwd ;
    }

    //return the email !!
    @Override
    public String getUsername() {
        return email;
    }

    //badalthaa true (non expired)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //kifkif badaltha true
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //kifkif badaltha true
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //kifkif badaltha true
    @Override
    public boolean isEnabled() {
        return true;
    }
}
