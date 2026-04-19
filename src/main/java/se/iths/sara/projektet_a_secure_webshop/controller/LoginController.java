package se.iths.sara.projektet_a_secure_webshop.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.iths.sara.projektet_a_secure_webshop.model.AppUser;
import se.iths.sara.projektet_a_secure_webshop.model.LoginToken;
import se.iths.sara.projektet_a_secure_webshop.service.AppUserService;
import se.iths.sara.projektet_a_secure_webshop.service.LoginTokenService;

import java.util.Optional;

@Controller
@RequestMapping
public class LoginController {

    private final AppUserService appUserService;
    private final LoginTokenService loginTokenService;
    private final PasswordEncoder passwordEncoder;

    public LoginController(AppUserService appUserService,
                           LoginTokenService loginTokenService,
                           PasswordEncoder passwordEncoder) {
        this.appUserService = appUserService;
        this.loginTokenService = loginTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/login-request")
    public String loginRequestPage() {
        return "redirect:/login";
    }

    @PostMapping("/login-request")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {

        Optional<AppUser> optionalUser = appUserService.findByEmail(email);

        if (optionalUser.isEmpty()) {
            model.addAttribute("error", "Fel email");
            return "login";
        }

        AppUser user = optionalUser.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("error", "Fel lösenord");
            return "login";
        }

        LoginToken token = loginTokenService.createToken(email);

        return "redirect:/verify-login?token=" + token.getToken();
    }
}