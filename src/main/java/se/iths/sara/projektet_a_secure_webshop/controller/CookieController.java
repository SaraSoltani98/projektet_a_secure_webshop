package se.iths.sara.projektet_a_secure_webshop.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static se.iths.sara.projektet_a_secure_webshop.util.CookieUtil.setConsentCookie;

@Controller
@RequestMapping("/cookies")
public class CookieController {

    @GetMapping
    public String cookiePage() {
        return "cookie";
    }

    @PostMapping("/consent")
    public String consent(HttpServletResponse response) {
        setConsentCookie(response, "true");
        return "redirect:/";
    }

    @PostMapping("/dissent")
    public String dissent(HttpServletResponse response) {
        setConsentCookie(response, "false");
        return "redirect:/";
    }
}
