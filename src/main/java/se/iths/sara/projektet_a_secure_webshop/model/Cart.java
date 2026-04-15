package se.iths.sara.projektet_a_secure_webshop.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> items = new ArrayList<>();

    public void addCartItem(Product product) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.increaseQuantity();
                return;
            }
        }
        items.add(new CartItem(product));
    }

    public void removeCartItem(Long productId) {
        items.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public BigDecimal getTotalAmount() {
        return items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<CartItem> getItems() {
        return items;
    }
}
