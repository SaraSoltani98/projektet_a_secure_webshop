package se.iths.sara.projektet_a_secure_webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.sara.projektet_a_secure_webshop.model.LoginToken;

import java.util.Optional;

public interface LoginTokenRepository extends JpaRepository<LoginToken, Long> {

    Optional<LoginToken> findByToken(String token);
}