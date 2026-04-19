package se.iths.sara.projektet_a_secure_webshop.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> cart = new ArrayList<>();

    public void addCartItem(Product product) {
        for (CartItem item : cart) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.increaseQuantity();
                return;
            }
        }
        cart.add(new CartItem(product));
    }

    public void removeCartItem(Long productId) {
        cart.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public BigDecimal getTotalAmount() {
        return cart.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<CartItem> getCart() {
        return cart;
    }
}
