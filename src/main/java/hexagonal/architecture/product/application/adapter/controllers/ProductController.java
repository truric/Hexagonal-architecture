package hexagonal.architecture.product.application.adapter.controllers;

import hexagonal.architecture.product.domain.dtos.ProductDTO;
import hexagonal.architecture.product.domain.dtos.StockDTO;
import hexagonal.architecture.product.domain.ports.interfaces.ProductServicePort;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductServicePort productServicePort;

    public ProductController(ProductServicePort productServicePort) {
        this.productServicePort = productServicePort;
    }

    @PostMapping
    ResponseEntity<String> createProducts(@Valid @RequestBody ProductDTO productDTO) {
        try {
            productServicePort.createProduct(productDTO);
            return ResponseEntity.ok("Product created successfully");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "SKU value must be unique", e);
        }
    }

    @GetMapping
    List<ProductDTO> getProducts() { return productServicePort.searchProducts(); }

    @GetMapping(value = "/{sku}")
    ProductDTO getProductBySku(@PathVariable String sku) throws NotFoundException {
        return productServicePort.getProductBySku(sku);
    }

    @PutMapping(value = "/{sku}")
    void updateStock (@PathVariable String sku, @RequestBody StockDTO stockDTO) throws NotFoundException {
        productServicePort.updateStock(sku, stockDTO);
    }

    @DeleteMapping(value = "/{sku}")
    void updateStock (@PathVariable String sku) throws NotFoundException {
        productServicePort.deleteBySku(sku);
    }
}
