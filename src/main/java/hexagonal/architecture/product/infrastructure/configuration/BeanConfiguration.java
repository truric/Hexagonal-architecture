package hexagonal.architecture.product.infrastructure.configuration;

import hexagonal.architecture.product.domain.adapters.services.ServiceRequestImp;
import hexagonal.architecture.product.domain.ports.interfaces.ProductServicePort;
import hexagonal.architecture.product.domain.ports.repositories.ProductRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    ProductServicePort productServicePort(ProductRepositoryPort productRepositoryPort) {
        return new ServiceRequestImp(productRepositoryPort);
    }
}
