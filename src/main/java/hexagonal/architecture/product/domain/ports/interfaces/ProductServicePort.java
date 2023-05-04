package hexagonal.architecture.product.domain.ports.interfaces;

import hexagonal.architecture.product.domain.dtos.ProductDTO;
import hexagonal.architecture.product.domain.dtos.StockDTO;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductServicePort {
    List<ProductDTO> searchProducts();

    ResponseEntity<String> createProduct(ProductDTO productDTO);
    void updateStock(String sku, StockDTO stockDTO) throws NotFoundException;
    ProductDTO getProductBySku(String sku) throws NotFoundException;

    void deleteBySku(String sku) throws NotFoundException;
}
