package se.iths.sara.projektet_a_secure_webshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.iths.sara.projektet_a_secure_webshop.model.Product;
import se.iths.sara.projektet_a_secure_webshop.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void shouldReturnProductById() {
        Product savedProduct = productRepository.save(
                new Product("Test", new BigDecimal("10000"), "Electronic", "http://")
        );

        Product result = productService.getProductById(savedProduct.getId());

        assertNotNull(result);
        assertEquals("Test", result.getName());
        assertEquals(new BigDecimal("10000.00"), result.getPrice());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.getProductById(999L)
        );

        assertEquals("Product with ID 999 not found", exception.getMessage());
    }

    @Test
    void shouldCreateProduct() {
        Product product = new Product("Test", new BigDecimal("11000"), "Electronic", "http://");

        Product savedProduct = productService.createProduct(product);

        assertNotNull(savedProduct.getId());
        assertEquals("Test", savedProduct.getName());
        assertEquals(1, productRepository.count());
    }

    @Test
    void shouldUpdateProduct() {
        Product savedProduct = productRepository.save(
                new Product("Test1", new BigDecimal("12000"), "Electronic", "http://")
        );

        savedProduct.setPrice(new BigDecimal("13000"));
        savedProduct.setName("Test2");

        Product updatedProduct = productService.updateProduct(savedProduct);

        assertEquals("Test2", updatedProduct.getName());
        assertEquals(new BigDecimal("13000"), updatedProduct.getPrice());
    }

    @Test
    void shouldDeleteProductById() {
        Product savedProduct = productRepository.save(
                new Product("Test", new BigDecimal("14000"), "Electronic", "http://")
        );

        productService.deleteProductById(savedProduct.getId());

        assertFalse(productRepository.findById(savedProduct.getId()).isPresent());
    }
}