package se.iths.sara.projektet_a_secure_webshop.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        String email = authentication.getName();
        model.addAttribute("email", email);
        return "profile";
    }
}