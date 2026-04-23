package se.iths.sara.projektet_a_secure_webshop.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static se.iths.sara.projektet_a_secure_webshop.util.CookieUtil.hasResponded;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home(HttpServletRequest request, Model model) {
        model.addAttribute("hasResponded", hasResponded(request));
        return "home";
    }

    @GetMapping("/policy")
    public String policyHome() {
        return "policy";
    }
}