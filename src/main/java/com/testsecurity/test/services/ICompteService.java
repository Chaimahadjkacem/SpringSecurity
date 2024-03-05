package com.testsecurity.test.services;

import com.testsecurity.test.entities.Compte;

public interface ICompteService {

    public Compte CreateForReset(Compte compte) ;

    public Compte findByResetToken(String resetToken) ;

    public Compte Create(Compte c) ;


}
