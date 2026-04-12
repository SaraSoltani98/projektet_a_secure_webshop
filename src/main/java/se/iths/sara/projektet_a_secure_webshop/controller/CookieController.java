package se.iths.sara.projektet_a_secure_webshop.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import static se.iths.sara.projektet_a_secure_webshop.util.CookieUtil.setConsentCookie;

@Controller
public class CookieController {

    @PostMapping("/cookies/consent")
    public String consent(HttpServletResponse response) {
        setConsentCookie(response, "true");
        return "redirect:/";
    }

    @PostMapping("/cookies/dissent")
    public String dissent(HttpServletResponse response) {
        setConsentCookie(response, "false");
        return "redirect:/";
    }
}
