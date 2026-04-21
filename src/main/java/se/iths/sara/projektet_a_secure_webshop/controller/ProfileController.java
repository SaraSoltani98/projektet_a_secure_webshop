package se.iths.sara.projektet_a_secure_webshop.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.iths.martin.springmessenger.model.Email;
import se.iths.martin.springmessenger.service.MessageService;
import se.iths.sara.projektet_a_secure_webshop.model.AppUser;
import se.iths.sara.projektet_a_secure_webshop.service.AppUserService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final AppUserService appUserService;
    private final MessageService messageService;

    public ProfileController(AppUserService appUserService, MessageService messageService) {
        this.appUserService = appUserService;
        this.messageService = messageService;
    }

    @GetMapping
    public String profile(Model model, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        String email = authentication.getName();
        model.addAttribute("email", email);
        return "profile";
    }

    @PostMapping("/send-userinfo")
    public String sendUserDataEmail(Authentication authentication, RedirectAttributes redirectAttributes) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String email = authentication.getName();

        AppUser user = appUserService.findByEmail(email).orElse(null);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Something went wrong");
            return "redirect:/profile";
        }

        try {
            Email mail = new Email();
            mail.setRecipient(user.getEmail());
            mail.setSubject("Your user information");
            mail.setMessage(
                    "Here is your user information:\n\n" +
                            "Email: " + user.getEmail() + "\n" +
                            "Roles: " + user.getRole()
            );
            messageService.send(mail);
            redirectAttributes.addFlashAttribute("success", "Email sent!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Could not send email");
        }
        return "redirect:/profile";
    }

    @PostMapping("/delete")
    public String deleteAccount(
            Authentication authentication,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes) {
        if (authentication == null) {
            return "redirect:/login";
        }

        String email = authentication.getName();

        AppUser user = appUserService.findByEmail(email).orElse(null);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "User not found");
            return "redirect:/profile";
        }

        try {
            appUserService.deleteUser(user);
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            return "redirect:/login?deleted";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Could not delete account");
            return "redirect:/profile";
        }
    }
}