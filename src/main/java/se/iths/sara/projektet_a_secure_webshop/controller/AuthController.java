package se.iths.sara.projektet_a_secure_webshop.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.iths.sara.projektet_a_secure_webshop.model.AppUser;
import se.iths.sara.projektet_a_secure_webshop.model.LoginToken;
import se.iths.sara.projektet_a_secure_webshop.service.AppUserService;
import se.iths.sara.projektet_a_secure_webshop.service.LoginTokenService;

import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {

    private final AppUserService appUserService;
    private final LoginTokenService loginTokenService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    public AuthController(AppUserService appUserService,
                          LoginTokenService loginTokenService,
                          PasswordEncoder passwordEncoder) {
        this.appUserService = appUserService;
        this.loginTokenService = loginTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) String error,
                                @RequestParam(required = false) String deleted,
                                Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }

        if (deleted != null) {
            model.addAttribute("message", "Ditt konto har tagits bort.");
        }

        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/login-request")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {

        Optional<AppUser> optionalUser = appUserService.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return "redirect:/register?needRegister=true&email=" + email;
        }

        AppUser user = optionalUser.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("error", "Fel lösenord");
            return "login";
        }

        LoginToken token = loginTokenService.createToken(email);

        // Tillfällig lösning tills riktig mail-service är inkopplad
        System.out.println("===== LOGIN MAIL =====");
        System.out.println("Till: " + email);
        System.out.println("Klicka på länken:");
        System.out.println("http://localhost:8080/verify-login?token=" + token.getToken());
        System.out.println("======================");

        model.addAttribute("message", "En inloggningslänk har skickats till din email.");
        return "login";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String email,
                               @RequestParam String password,
                               @RequestParam(required = false, defaultValue = "false") boolean consent,
                               Model model) {
        try {
            AppUser user = new AppUser();
            user.setEmail(email);
            user.setPassword(password);
            user.setConsent(consent);

            appUserService.registerUser(user);
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/verify-login")
    public String verifyLoginToken(@RequestParam String token,
                                   Model model,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {

        Optional<LoginToken> optionalToken = loginTokenService.findByToken(token);

        if (optionalToken.isEmpty()) {
            model.addAttribute("error", "Ogiltig token.");
            return "login";
        }

        LoginToken loginToken = optionalToken.get();

        if (!loginTokenService.isValid(loginToken)) {
            model.addAttribute("error", "Token har gått ut eller redan använts.");
            return "login";
        }

        Optional<AppUser> optionalUser = appUserService.findByEmail(loginToken.getEmail());

        if (optionalUser.isEmpty()) {
            model.addAttribute("error", "Användaren finns inte.");
            return "login";
        }

        AppUser user = optionalUser.get();

        loginTokenService.markAsUsed(loginToken);

        String role = "ROLE_" + user.getRole().name();

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        null,
                        List.of(new SimpleGrantedAuthority(role))
                );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        if (user.getRole().name().equals("ADMIN")) {
            return "redirect:/admin";
        }

        return "redirect:/profile";
    }
}