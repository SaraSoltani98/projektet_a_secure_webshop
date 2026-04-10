package se.iths.sara.projektet_a_secure_webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.sara.projektet_a_secure_webshop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
