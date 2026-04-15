package se.iths.sara.projektet_a_secure_webshop.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.iths.sara.projektet_a_secure_webshop.model.Product;
import se.iths.sara.projektet_a_secure_webshop.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showAdminPage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin";
    }

    @PostMapping("/products")
    public String createProduct(@Valid @ModelAttribute("product") Product product) {
        productService.createProduct(product);
        return "redirect:/admin";
    }
}
