package se.iths.sara.projektet_a_secure_webshop.service;

import org.junit.jupiter.api.Test;
import se.iths.sara.projektet_a_secure_webshop.model.Product;
import se.iths.sara.projektet_a_secure_webshop.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    ProductRepository productRepository = mock(ProductRepository.class);
    ProductService productService = new ProductService(productRepository);

    @Test
    void ShouldReturnAllProducts() {
        when(productRepository.findAllByOrderByCategoryAscNameAsc()).thenReturn(List.of(
                new Product("Test1", new BigDecimal(10), "Electronic", "Url"),
                new Product("Test2", new BigDecimal(10), "Electronic", "Url"),
                new Product("Test3", new BigDecimal(10), "Electronic", "Url")
        ));

        List<Product> result = productService.getAllProductsSorted();

        assertEquals(3, result.size());
        verify(productRepository, times(1)).findAllByOrderByCategoryAscNameAsc();
    }

    
}
