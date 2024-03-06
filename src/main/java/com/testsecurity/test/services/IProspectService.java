package com.testsecurity.test.services;

import com.testsecurity.test.configurations.Mail;
import com.testsecurity.test.entities.Prospect;

import javax.mail.MessagingException;

public interface IProspectService {

    public void sendEmail(Mail mail, String templateName) throws MessagingException ;
    public void addProspectAndCompte(Prospect prospect) throws MessagingException;
}
