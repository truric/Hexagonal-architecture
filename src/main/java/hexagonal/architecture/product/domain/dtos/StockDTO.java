package hexagonal.architecture.product.domain.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class StockDTO {
    @NotNull
    @Positive
    private Double quantity;
    public Double getQuantity() { return quantity; }
}
