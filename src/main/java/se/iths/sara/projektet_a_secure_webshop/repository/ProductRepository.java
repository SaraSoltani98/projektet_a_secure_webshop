package se.iths.sara.projektet_a_secure_webshop.repository;

import org.springframework.data.repository.CrudRepository;
import se.iths.sara.projektet_a_secure_webshop.model.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByUser_id(Long id);
}
