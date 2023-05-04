package hexagonal.architecture.product.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductDTO {
    private String sku;
    private String name;
    private Double price;
    private Double quantity;
}
