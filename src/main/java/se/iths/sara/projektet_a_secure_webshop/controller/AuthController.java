package se.iths.sara.projektet_a_secure_webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.iths.sara.projektet_a_secure_webshop.model.AppUser;
import se.iths.sara.projektet_a_secure_webshop.service.AppUserService;

@Controller
@RequestMapping("/register")
public class AuthController {

    private final AppUserService appUserService;

    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping
    public String registerUser(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) boolean consent
    ) {

        AppUser user = new AppUser();
        user.setEmail(email);
        user.setPassword(password);
        user.setConsent(consent);
        user.setRole("USER");

        appUserService.registerUser(user);

        return "redirect:/login";
    }
}