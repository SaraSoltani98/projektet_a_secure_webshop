package se.iths.sara.projektet_a_secure_webshop.model;

import java.math.BigDecimal;

public class CartItem {

    private Product product;
    private int quantity;

    public CartItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public void increaseQuantity() {
        this.quantity++;
    }

    public BigDecimal getTotalPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
