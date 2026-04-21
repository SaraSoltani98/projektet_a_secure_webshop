package se.iths.sara.projektet_a_secure_webshop.service;

import org.springframework.stereotype.Service;
import se.iths.martin.springmessenger.model.Email;
import se.iths.martin.springmessenger.service.MessageService;
import se.iths.sara.projektet_a_secure_webshop.model.Cart;
import se.iths.sara.projektet_a_secure_webshop.model.Order;
import se.iths.sara.projektet_a_secure_webshop.model.OrderItem;
import se.iths.sara.projektet_a_secure_webshop.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MessageService messageService;

    public OrderService(OrderRepository orderRepository, MessageService messageService) {
        this.orderRepository = orderRepository;
        this.messageService = messageService;
    }

    public Order checkout(Cart cart, String username) {
        if (cart == null || cart.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty.");
        }
        List<OrderItem> items = cart.getItems().stream().map(item -> new OrderItem(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getProduct().getPrice()))
                .toList();
        Order order = new Order(
                username,
                cart.getTotalAmount(),
                LocalDateTime.now(),
                items
        );

        Order savedOrder = orderRepository.save(order);
        cart.clear();
        Email email = new Email();
        email.setRecipient(username);
        email.setSubject("Your order");
        email.setMessage(savedOrder.toMail());
        messageService.send(email);

        return savedOrder;

    }
}
