package se.iths.sara.projektet_a_secure_webshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import se.iths.sara.projektet_a_secure_webshop.model.LoginToken;
import se.iths.sara.projektet_a_secure_webshop.service.AppUserDatabaseService;
import se.iths.sara.projektet_a_secure_webshop.service.LoginTokenService;

@Configuration
public class SecurityConfig {

    private final LoginTokenService loginTokenService;

    public SecurityConfig(LoginTokenService loginTokenService) {
        this.loginTokenService = loginTokenService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationProvider authenticationProvider) throws Exception {
        http
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/login", "/login-request", "/register", "/error", "/images/**", "/verify-login").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {
                            String email = authentication.getName();

                            LoginToken token = loginTokenService.createToken(email);

                            response.sendRedirect("/verify-login?token=" + token.getToken());
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.sendRedirect("/"))
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(AppUserDatabaseService appUserDatabaseService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(appUserDatabaseService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}