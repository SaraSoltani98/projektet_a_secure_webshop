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

    public List<Product> findByUser_Id(Long id) {
        return productRepository.findByUser_Id(id);
    }
}
