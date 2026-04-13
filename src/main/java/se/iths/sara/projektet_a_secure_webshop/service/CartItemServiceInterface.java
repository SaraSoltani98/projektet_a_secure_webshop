package se.iths.sara.projektet_a_secure_webshop.service;

public interface CartItemServiceInterface {
    void addItemToCart(Long cartId, Long productId, int quantity);

    void removeItemFromCart(Long cartId, Long productId);

    void updateItemQuantity(Long cartId, Long productId, int quantity);
}
