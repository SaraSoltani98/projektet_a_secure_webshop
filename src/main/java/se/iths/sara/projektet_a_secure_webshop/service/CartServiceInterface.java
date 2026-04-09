package se.iths.sara.projektet_a_secure_webshop.service;

import se.iths.sara.projektet_a_secure_webshop.model.Cart;

import java.math.BigDecimal;

public interface CartServiceInterface {
    Cart getCart(Long id);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);
}
