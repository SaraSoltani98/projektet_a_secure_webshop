package se.iths.sara.projektet_a_secure_webshop.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.iths.sara.projektet_a_secure_webshop.model.Cart;
import se.iths.sara.projektet_a_secure_webshop.model.Order;
import se.iths.sara.projektet_a_secure_webshop.model.Product;
import se.iths.sara.projektet_a_secure_webshop.repository.OrderRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService checkoutService;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testCheckout() {
        Cart cart = new Cart();
        Product product = new Product(
                "TV", new BigDecimal(15000),
                "Electronics", "url123");
        cart.addCartItem(product);
        Order order = checkoutService.checkout(cart, "martin.gruter@gmail.com");
        assertEquals(new BigDecimal(15000), order.getTotalPrice());
        assertEquals(1, orderRepository.findAll().size());
    }
}
