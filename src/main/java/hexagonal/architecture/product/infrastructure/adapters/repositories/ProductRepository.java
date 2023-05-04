package hexagonal.architecture.product.infrastructure.adapters.repositories;

import hexagonal.architecture.product.domain.Product;
import hexagonal.architecture.product.domain.dtos.ProductDTO;
import hexagonal.architecture.product.domain.ports.repositories.ProductRepositoryPort;
import hexagonal.architecture.product.infrastructure.adapters.entities.ProductEntity;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductRepository implements ProductRepositoryPort {
    private final ProductRepositoryInterface productRepositoryInterface;

    public ProductRepository(ProductRepositoryInterface productRepositoryInterface) {
        this.productRepositoryInterface = productRepositoryInterface;
    }

    @Override
    public List<Product> getAllProducts() {
        List<ProductEntity> productEntities = this.productRepositoryInterface.findAll();
        return productEntities.stream().map(ProductEntity::toProduct).collect(Collectors.toList());
    }

    @Override
    public Product searchBySku(String sku) {
        Optional<ProductEntity> productEntity = this.productRepositoryInterface.findBySku(sku);

        if (productEntity.isPresent())
            return productEntity.get().toProduct();

        throw new RuntimeException("Product not found");
    }

    @Override
    public void save(Product product) {
        ProductEntity productEntity;
        if (Objects.isNull(product.getCode()))
            productEntity = new ProductEntity(product);
        else {
            productEntity = this.productRepositoryInterface.findById(product.getCode()).get();
            productEntity.update(product);
        }

        this.productRepositoryInterface.save(productEntity);
    }

    @Override
    public void deleteBySku(String sku) throws NotFoundException {
        Optional<ProductEntity> product = productRepositoryInterface.findBySku(sku);

        if (product.isEmpty()) {
            throw new NotFoundException("Product not found");
        }

        productRepositoryInterface.delete(product.get());
    }
}
