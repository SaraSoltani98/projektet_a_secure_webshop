package se.iths.sara.projektet_a_secure_webshop.service;

import org.springframework.stereotype.Service;
import se.iths.sara.projektet_a_secure_webshop.model.Product;
import se.iths.sara.projektet_a_secure_webshop.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProductsSorted() {
        return productRepository.findAllByOrderByCategoryAscNameAsc();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " not found"));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}