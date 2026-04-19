package se.iths.sara.projektet_a_secure_webshop.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    public AuthController(AppUserService appUserService, LoginTokenService loginTokenService) {
        this.appUserService = appUserService;
        this.loginTokenService = loginTokenService;
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false, defaultValue = "false") boolean consent,
            Model model
    ) {
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
    public String verifyLoginToken(@RequestParam String token, Model model, HttpServletRequest request,
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

        loginTokenService.markAsUsed(loginToken);

        String role = "ROLE_USER";

        if (loginToken.getEmail().equals("test@admin.com")) {
            role = "ROLE_ADMIN";
        }

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        loginToken.getEmail(),
                        null,
                        List.of(new SimpleGrantedAuthority(role))
                );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        if (role.equals("ROLE_ADMIN")) {
            return "redirect:/admin";
        }
        return "redirect:/";
    }
}