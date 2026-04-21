package se.iths.sara.projektet_a_secure_webshop.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        String email = authentication.getName();
        model.addAttribute("email", email);
        return "profile";
    }

    @PostMapping("/profile/send-email")
    public String sendUserDataEmail(Authentication authentication) {
        String email = authentication.getName();

        // TODO: koppla mail-service
        System.out.println("Skicka mail till: " + email);

        return "redirect:/profile";
    }

    @PostMapping("/profile/delete")
    public String deleteAccount(Authentication authentication) {
        String email = authentication.getName();

        // TODO: radera user från DB
        System.out.println("Ta bort konto: " + email);

        return "redirect:/login?deleted";
    }
}