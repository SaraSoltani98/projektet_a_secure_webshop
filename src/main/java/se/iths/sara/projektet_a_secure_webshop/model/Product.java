package se.iths.sara.projektet_a_secure_webshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Type in a product name")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Type in a valid price")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price has to be greater than 0")
    @Column(nullable = false)
    private BigDecimal price;

    @NotBlank(message = "Type in a category")
    @Column(nullable = false)
    private String category;

    @NotBlank(message = "Type in a valid URL")
    @Pattern(
            regexp = "^(http|https)://.*$",
            message = "URL must begin with http:// or https://"
    )
    @Column(nullable = false)
    private String imageUrl;

    public Product() {
        // Empty constructor
    }

    public Product(String name, BigDecimal price, String category, String imageUrl) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
