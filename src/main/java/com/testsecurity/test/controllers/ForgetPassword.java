package com.testsecurity.test.controllers;


import com.testsecurity.test.API.MailSenderService;
import com.testsecurity.test.Dto.auth.AccountResponse;
import com.testsecurity.test.Dto.auth.ResetPassword;
import com.testsecurity.test.entities.Compte;
import com.testsecurity.test.repositories.CompteRepository;
import com.testsecurity.test.services.CompteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("ForgetPassword")
@CrossOrigin(origins = "*")
public class ForgetPassword {

    public final CompteService compteService;

    @Autowired
    MailSenderService mailSenderService;

    public final CompteRepository compteRepository;


    PasswordEncoder passwordEncoder;

    {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }



    @PostMapping("checkEmail")
    public String resetPasswordEmail(@RequestBody ResetPassword resetPassword, HttpServletRequest request) {
        Compte compte = compteService.findByEmail(resetPassword.getEmail());
        AccountResponse accountResponse = new AccountResponse();
        if (compte != null) {
            // String code = UserCode.getCode();
            compte.setResetToken(UUID.randomUUID().toString());
            compteService.CreateForReset(compte);
            String appUrl = request.getScheme() + "://" + request.getServerName();
            mailSenderService.sendEmail(resetPassword.getEmail(), "Forget password", "To reset your password, check your token :\n" +
                    "token=" + compte.getResetToken());
            // ResetTwilio.sendSMS("To reset your password, check your code : code=" + user.getResetToken());
            accountResponse.setResult("User Found");
        } else {
            accountResponse.setResult("Forgot Password");
        }
        return "redirect:/forgot-password?success";
    }

    @GetMapping("resetPassword")
    public Map<String, Boolean> resetPassword(@RequestParam(required = false) String resetToken) {
        Map<String, Boolean> response = new HashMap<>();
        Compte compte = this.compteService.findByResetToken(resetToken);
        if (compte == null) {
            response.put("isValidToken", false);
        } else {
            response.put("isValidToken", true);
        }
        return response;
    }

    @GetMapping("/changePass")
    public String processResetPassword(@RequestParam("resetToken") String resetToken, @RequestParam String Password ) {

        if(compteService.validatePassword(Password)) {

            AccountResponse accountResponse = new AccountResponse();


            // Find the user associated with the reset token
            Compte compte = compteService.findByResetToken(resetToken);

            if (compte != null) {
                String a = this.passwordEncoder.encode(Password);
                System.out.println(a);
                compte.setMdp(a);
                compteRepository.save(compte);
            }
            return "password changed";
        }
        return "password error";
    }


}
