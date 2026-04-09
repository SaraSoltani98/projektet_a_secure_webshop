package se.iths.sara.projektet_a_secure_webshop.service;

import org.springframework.stereotype.Service;
import se.iths.sara.projektet_a_secure_webshop.model.Cart;
import se.iths.sara.projektet_a_secure_webshop.repository.CartItemRepository;
import se.iths.sara.projektet_a_secure_webshop.repository.CartRepository;

import java.math.BigDecimal;

@Service
public class CartService implements CartServiceInterface {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }
}
