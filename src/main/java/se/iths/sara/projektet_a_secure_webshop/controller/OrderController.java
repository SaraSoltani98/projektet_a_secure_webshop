package se.iths.sara.projektet_a_secure_webshop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.iths.sara.projektet_a_secure_webshop.model.Cart;
import se.iths.sara.projektet_a_secure_webshop.model.Order;
import se.iths.sara.projektet_a_secure_webshop.service.OrderService;

import java.util.Optional;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public String checkout(HttpSession session, Authentication authentication, RedirectAttributes redirectAttributes) {
        Cart cart = (Cart) session.getAttribute("cart");
        try {
            Order order = orderService.checkout(cart, authentication.getName());
            redirectAttributes.addFlashAttribute("order", order);
            return "redirect:/order/confirmation/" + order.getId();
        } catch (IllegalArgumentException e) {
            return "redirect:/cart";
        }
    }

    @GetMapping("/confirmation/{id}")
    public String confirmation(Model model, @PathVariable Long id) {

        Optional<Order> foundOrder = orderService.getOrder(id);
        if (foundOrder.isEmpty()) {
            return "redirect:/cart";
        }
        Order order = foundOrder.get();

        model.addAttribute("order", order);
        return "confirmation";
    }

}
