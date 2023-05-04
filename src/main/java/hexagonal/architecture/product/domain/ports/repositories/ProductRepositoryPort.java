package hexagonal.architecture.product.domain.ports.repositories;

import hexagonal.architecture.product.domain.Product;

import java.util.List;

public interface ProductRepositoryPort {
    List<Product> getAllProducts();

    Product searchBySku(String sku);
    void save(Product product);
}
