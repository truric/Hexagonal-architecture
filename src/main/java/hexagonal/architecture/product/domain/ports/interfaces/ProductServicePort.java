package hexagonal.architecture.product.domain.ports.interfaces;

import hexagonal.architecture.product.domain.dtos.ProductDTO;
import hexagonal.architecture.product.domain.dtos.StockDTO;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface ProductServicePort {
    List<ProductDTO> searchProducts();

    void createProduct(ProductDTO productDTO);
    void updateStock(String sku, StockDTO stockDTO) throws ChangeSetPersister.NotFoundException;
}
