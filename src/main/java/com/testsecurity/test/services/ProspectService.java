package com.testsecurity.test.services;

import com.testsecurity.test.configurations.Mail;
import com.testsecurity.test.entities.Compte;
import com.testsecurity.test.entities.Prospect;
import com.testsecurity.test.repositories.CompteRepository;
import com.testsecurity.test.repositories.ProspectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProspectService implements IProspectService {

    public final ProspectRepository prospectRepository ;
    public final CompteRepository compteRepository ;


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    ////ENAAAAAAA

    ///////Email -------
    @Override
    public void sendEmail(Mail mail, String templateName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(mail.getProps());
        helper.setFrom(mail.getFrom());
        helper.setTo(mail.getMailTo());
        helper.setSubject(mail.getSubject());
        String html = templateEngine.process(templateName, context);
        helper.setText(html, true);
        mailSender.send(message);
    }


    @Override
    public void addProspectAndCompte(Prospect prospect ) throws MessagingException {
        prospectRepository.save(prospect);
        Compte c = new Compte();
        c.setEmail(prospect.getEmail());

        String rawPassword = prospect.getNom() + prospect.getPrenom();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(rawPassword);

        c.setMdp(encryptedPassword);

        compteRepository.save(c);

        ///////////////////////////////////////////////// Mail d'enregistrement

        Mail mail = new Mail();

        mail.setFrom("shaimahajkacem@gmail.com");
        mail.setMailTo(prospect.getEmail());
        mail.setSubject("Creation du compte");

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("name", prospect.getNom() + " " + prospect.getPrenom());
        mail.setProps(model);

        sendEmail(mail, "register.html");

    }

}
