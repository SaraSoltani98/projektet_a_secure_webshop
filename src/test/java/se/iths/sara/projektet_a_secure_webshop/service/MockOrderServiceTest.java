package se.iths.sara.projektet_a_secure_webshop.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.iths.martin.springmessenger.service.MessageService;
import se.iths.sara.projektet_a_secure_webshop.model.Cart;
import se.iths.sara.projektet_a_secure_webshop.model.Order;
import se.iths.sara.projektet_a_secure_webshop.model.Product;
import se.iths.sara.projektet_a_secure_webshop.repository.OrderRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MockOrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private OrderService orderService;

    @Test
    void checkoutMethodCreateOrderAndClearCartTest() {
        Cart cart = new Cart();
        cart.addCartItem(new Product("TV", new BigDecimal(15000),
                "Electronics", "URL123"));

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            return order;
        });
        Order result = orderService.checkout(cart, "martin");

        assertNotNull(result);
        assertEquals("martin", result.getUsername());
    }
}
