package se.iths.sara.projektet_a_secure_webshop.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendLoginLink(String email, String token) {
        String link = "http://localhost:8080/verify-login?token=" + token;

        // 🔥 TEMPORÄR lösning (simulerar mail)
        System.out.println("===== MAIL =====");
        System.out.println("Till: " + email);
        System.out.println("Klicka på länken:");
        System.out.println(link);
        System.out.println("================");
    }
}