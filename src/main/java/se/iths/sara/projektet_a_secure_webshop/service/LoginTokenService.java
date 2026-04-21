package se.iths.sara.projektet_a_secure_webshop.service;

import org.springframework.stereotype.Service;
import se.iths.sara.projektet_a_secure_webshop.model.LoginToken;
import se.iths.sara.projektet_a_secure_webshop.repository.LoginTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoginTokenService {
    private final LoginTokenRepository loginTokenRepository;

    public LoginTokenService(LoginTokenRepository loginTokenRepository) {
        this.loginTokenRepository = loginTokenRepository;
    }

    public LoginToken createToken(String email) {
        String token = UUID.randomUUID().toString();

        LoginToken loginToken = new LoginToken();
        loginToken.setToken(token);
        loginToken.setEmail(email);
        loginToken.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        loginToken.setUsed(false);

        return loginTokenRepository.save(loginToken);
    }

    public Optional<LoginToken> findByToken(String token) {

        return loginTokenRepository.findByToken(token);
    }

    public boolean isValid(LoginToken loginToken) {
        return !loginToken.isUsed() && loginToken.getExpiresAt().isAfter(LocalDateTime.now());
    }

    public void markAsUsed(LoginToken loginToken) {
        loginToken.setUsed(true);
        loginTokenRepository.save(loginToken);
    }
}