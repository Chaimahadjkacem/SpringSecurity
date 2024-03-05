package com.testsecurity.test.controllers;

import com.testsecurity.test.entities.Prospect;
import com.testsecurity.test.services.CompteService;
import com.testsecurity.test.services.ProspectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compte")
@CrossOrigin(origins = "*")
public class CompteRestController {

    public final ProspectService prospectService;


    @PostMapping("/addProsAndCompte")
     void AddEvent(@RequestBody Prospect p) {
         prospectService.addProspectAndCompte(p);
    }



}
