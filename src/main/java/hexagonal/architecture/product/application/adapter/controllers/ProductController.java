package hexagonal.architecture.product.application.adapter.controllers;

import hexagonal.architecture.product.domain.dtos.ProductDTO;
import hexagonal.architecture.product.domain.dtos.StockDTO;
import hexagonal.architecture.product.domain.ports.interfaces.ProductServicePort;
import jakarta.ws.rs.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductServicePort productServicePort;

    public ProductController(ProductServicePort productServicePort) {
        this.productServicePort = productServicePort;
    }

    @PostMapping
    void createProducts(@RequestBody ProductDTO productDTO) {
        productServicePort.createProduct(productDTO);
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
