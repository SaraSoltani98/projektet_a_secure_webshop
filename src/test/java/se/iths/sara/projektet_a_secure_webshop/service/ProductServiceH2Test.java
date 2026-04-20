package se.iths.sara.projektet_a_secure_webshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.iths.sara.projektet_a_secure_webshop.model.Product;
import se.iths.sara.projektet_a_secure_webshop.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProductServiceH2Test {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void shouldReturnAllProductsSorted() {
        productRepository.save(new Product("Test1", new BigDecimal("1000"), "Electronic", "http://"));
        productRepository.save(new Product("Test2", new BigDecimal("2000"), "Electronic", "http://"));
        productRepository.save(new Product("Test3", new BigDecimal("3000"), "Electronic", "http://"));

        List<Product> result = productService.getAllProductsSorted();

        assertEquals(3, result.size());
        assertEquals("Test2", result.get(1).getName());
        assertEquals("Test3", result.get(2).getName());
        assertEquals("Test1", result.get(0).getName());
    }
}