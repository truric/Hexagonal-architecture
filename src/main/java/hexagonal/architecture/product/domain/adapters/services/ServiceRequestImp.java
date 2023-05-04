package hexagonal.architecture.product.domain.adapters.services;

import hexagonal.architecture.product.domain.Product;
import hexagonal.architecture.product.domain.dtos.ProductDTO;
import hexagonal.architecture.product.domain.dtos.StockDTO;
import hexagonal.architecture.product.domain.ports.interfaces.ProductServicePort;
import hexagonal.architecture.product.domain.ports.repositories.ProductRepositoryPort;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ServiceRequestImp implements ProductServicePort {
    private final ProductRepositoryPort productRepositoryPort;

    public ServiceRequestImp(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    public void createProduct(ProductDTO productDTO) {
        Product product = new Product(productDTO);
        this.productRepositoryPort.save(product);
    }

    @Override
    public List<ProductDTO> searchProducts() {
        List<Product> products = this.productRepositoryPort.getAllProducts();
        List<ProductDTO> productDTOS = products.stream().map(Product::toProductDTO).collect(Collectors.toList());
        return productDTOS;
    }

    @Override
    public void updateStock(String sku, StockDTO stockDTO) throws NotFoundException {
        Product product = this.productRepositoryPort.searchBySku(sku);

        if (Objects.isNull(product))
            throw new NotFoundException("Product not found");

        product.updateStock(stockDTO.getQuantity());
        this.productRepositoryPort.save(product);
    }
}