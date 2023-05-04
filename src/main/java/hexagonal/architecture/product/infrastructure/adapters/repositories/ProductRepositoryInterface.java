package hexagonal.architecture.product.infrastructure.adapters.repositories;

import hexagonal.architecture.product.infrastructure.adapters.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepositoryInterface extends JpaRepository<ProductEntity, UUID> {
    Optional<ProductEntity> findBySku(String sku);

    void deleteBySku(String sku);
}
