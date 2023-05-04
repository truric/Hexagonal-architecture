package hexagonal.architecture.product.infrastructure.adapters.entities;

import hexagonal.architecture.product.domain.Product;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "products")
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID code;
    @Column(unique = true)
    private String sku;
    private String name;
    private Double price;
    private Double quantity;

    public ProductEntity(Product product) {
        this.sku = product.getSku();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
    }

    public void update(Product product) {
        this.sku = product.getSku();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
    }

    public Product toProduct() {
        return new Product(this.code, this.sku, this.name, this.price, this.quantity);
    }
}
