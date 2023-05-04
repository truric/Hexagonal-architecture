package hexagonal.architecture.product.domain.ports.repositories;

import hexagonal.architecture.product.domain.Product;
import hexagonal.architecture.product.domain.dtos.ProductDTO;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

public interface ProductRepositoryPort {
    List<Product> getAllProducts();

    Product searchBySku(String sku);
    void save(Product product);
    void deleteBySku(String sku) throws NotFoundException;

}
