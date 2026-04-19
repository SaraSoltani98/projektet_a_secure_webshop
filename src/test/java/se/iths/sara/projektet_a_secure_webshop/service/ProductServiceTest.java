package se.iths.sara.projektet_a_secure_webshop.service;

import org.junit.jupiter.api.Test;
import se.iths.sara.projektet_a_secure_webshop.model.Product;
import se.iths.sara.projektet_a_secure_webshop.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    ProductRepository productRepository = mock(ProductRepository.class);
    ProductService productService = new ProductService(productRepository);

    @Test
    void ShouldReturnAllProducts() {
        when(productRepository.findAllByOrderByCategoryAscNameAsc()).thenReturn(List.of(
                new Product("Test1", new BigDecimal("1000"), "Electronic", "Url"),
                new Product("Test2", new BigDecimal("2000"), "Electronic", "Url"),
                new Product("Test3", new BigDecimal("3000"), "Electronic", "Url")
        ));

        List<Product> result = productService.getAllProductsSorted();

        assertEquals(3, result.size());
        verify(productRepository, times(1)).findAllByOrderByCategoryAscNameAsc();
    }

    @Test
    void shouldReturnProductById() {
        Product product = new Product("Test", new BigDecimal("10000"), "Electronic", "Url");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Test", result.getName());
        assertEquals(new BigDecimal("10000"), result.getPrice());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFoundById() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.getProductById(99L)
        );

        assertEquals("Product with ID 99 not found", exception.getMessage());
        verify(productRepository, times(1)).findById(99L);
    }

    @Test
    void shouldCreateProduct() {
        Product product = new Product("Test", new BigDecimal("11000"), "Electronic", "Url");

        when(productRepository.save(product)).thenReturn(product);

        Product saved = productService.createProduct(product);

        assertNotNull(saved);
        assertEquals("Test", saved.getName());
        verify(productRepository).save(product);
    }

    @Test
    void shouldUpdateProduct() {
        Product product = new Product("Test", new BigDecimal("12000"), "Electronic", "Url");

        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.updateProduct(product);

        assertNotNull(result);
        assertEquals(new BigDecimal("12000"), result.getPrice());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void shouldDeleteProductById() {
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProductById(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }
}
