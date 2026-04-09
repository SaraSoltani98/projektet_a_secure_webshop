package se.iths.sara.projektet_a_secure_webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.sara.projektet_a_secure_webshop.model.AppUser;
import se.iths.sara.projektet_a_secure_webshop.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<AppUser, Long> {
    List<Product> findByUser_Id(Long id);
}
