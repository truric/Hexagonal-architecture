package hexagonal.architecture.product.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductDTO {
    @NotBlank
    private String sku;

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    @Positive
    private Double quantity;
}
