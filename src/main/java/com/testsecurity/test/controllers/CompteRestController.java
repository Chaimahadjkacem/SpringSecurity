package com.testsecurity.test.controllers;

import com.testsecurity.test.entities.Prospect;
import com.testsecurity.test.services.CompteService;
import com.testsecurity.test.services.ProspectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compte")
@CrossOrigin(origins = "*")
public class CompteRestController {

    public final ProspectService prospectService;


    @PostMapping("/addProsAndCompte")
     void AddEvent(@RequestBody Prospect p) throws MessagingException {
         prospectService.addProspectAndCompte(p);
    }

    @PostMapping("/confMdp")
    public String registerUser(@RequestParam("password") String password,
                               @RequestParam("confirmPassword") String confirmPassword) {

        // Vérifiez si les mots de passe correspondent
        if (!password.equals(confirmPassword)) {
            // Gérer le cas où les mots de passe ne correspondent pas
            return "redirect:/register?error=PasswordsDoNotMatch";
        }

        // Enregistrez le mot de passe, par exemple, dans une base de données ou un service d'utilisateur
        // Pour simplifier, nous allons simplement imprimer le mot de passe ici
        System.out.println("Mot de passe enregistré : " + password);

        // Redirigez vers une page de réussite ou une autre page appropriée
        return "redirect:/registration-success";
    }



}
