package se.iths.sara.projektet_a_secure_webshop.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;

public class CookieUtil {
    private static final String COOKIE_NAME = "cookie_consent";

    public static void setConsentCookie(HttpServletResponse response, String value) {
        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, value)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(60 * 60 * 24 * 7)
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

    public static String getCookieValue(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(COOKIE_NAME)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public static boolean hasResponded(HttpServletRequest request) {
        return getCookieValue(request) != null;
    }
}
