package hexagonal.architecture.product.domain;

import hexagonal.architecture.product.domain.dtos.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private UUID code;
    private String sku;
    private String name;
    private Double price;
    private Double quantity;

    public Product(ProductDTO productDTO) {
        this.sku = productDTO.getSku();
        this.name = productDTO.getName();
        this.price = productDTO.getPrice();
        this.quantity = productDTO.getQuantity();
    }
    public void updateStock(double quantity) { this.quantity = quantity; }

    public ProductDTO toProductDTO() {
        return new ProductDTO(this.sku, this.name, this.price, this.quantity);
    }

}
