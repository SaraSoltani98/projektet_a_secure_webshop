package se.iths.sara.projektet_a_secure_webshop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.iths.sara.projektet_a_secure_webshop.model.Cart;
import se.iths.sara.projektet_a_secure_webshop.model.Order;
import se.iths.sara.projektet_a_secure_webshop.service.OrderService;

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
            return "redirect:/order/confirmation";
        } catch (IllegalArgumentException e) {
            return "redirect:/cart";
        }
    }

    @GetMapping("/confirmation")
    public String confirmation() {
        return "confirmation";
    }

}
