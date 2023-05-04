package hexagonal.architecture.product.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexagonal.architecture.product.domain.Product;
import hexagonal.architecture.product.domain.dtos.ProductDTO;
import hexagonal.architecture.product.domain.ports.interfaces.ProductServicePort;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
/**
 * When you use @SpringBootTest, it loads the complete Spring application context for your tests.
 * This includes all the beans and configurations defined in your application, not just the mocks.
 * The @DirtiesContext annotation with classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD indicates that the
 * application context should be considered dirty and reset before each test method
 */
class ProductCreationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProductServicePort productServicePort;

	private ObjectMapper objectMapper;
	private ProductDTO productDTO;

	@BeforeEach
	public void setup() {
		objectMapper = new ObjectMapper();
		productDTO = new ProductDTO("SKU123", "Existing Product", 9.99, 2D);
	}

	@Test
	public void testCreateProductWithUniqueSKU() throws Exception {
		mockMvc.perform(post("/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(productDTO)))
				.andExpect(status().isOk())
				.andExpect(content().string("Product created successfully"));
	}

	@Test
	void testCreateProductWithDuplicateSKU() throws Exception {
		mockMvc.perform(post("/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(productDTO)))
				.andExpect(status().isOk());

		mockMvc.perform(post("/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(productDTO)))
				.andExpect(status().isInternalServerError())
				.andExpect(result -> {
					Throwable exception = result.getResolvedException();
					assertTrue(exception instanceof ResponseStatusException);
					ResponseStatusException responseStatusException = (ResponseStatusException) exception;
					assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseStatusException.getStatusCode());
					assertEquals("SKU value must be unique", responseStatusException.getReason());
				});
	}

	@Test
	public void testCreateProduct_Success() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(productDTO)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Product created successfully"));
	}

	@Test
	void testCreateProduct_InvalidInputData() throws Exception {
		String requestBody = "{\"price\": 10.99, \"quantity\": 5, \"code\": \"CODE123\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void testCreateProduct_EdgeCases() {
		// Test with maximum allowed values
		Product product1 = new Product();
		product1.setName("Lorem ipsum dolor sit amet");
		product1.setPrice(Double.MAX_VALUE);
		product1.setQuantity(Double.MAX_VALUE);

		ResponseEntity<String> createdProduct1 = productServicePort.createProduct(product1.toProductDTO());
		assertNotNull(createdProduct1);
		assertEquals(HttpStatus.OK, createdProduct1.getStatusCode());
		assertNotNull(createdProduct1.getBody());

		// Test with empty values
		Product product2 = new Product();
		product2.setName("");
		product2.setPrice(0.0);
		product2.setQuantity(0D);

		ResponseEntity<String> createdProduct2 = productServicePort.createProduct(product2.toProductDTO());
		assertNotNull(createdProduct2);
		assertEquals(HttpStatus.OK, createdProduct2.getStatusCode());
		assertNotNull(createdProduct2.getBody());

		// Test with null values
		assertThrows(RuntimeException.class, () -> {
			productServicePort.createProduct(null);
		});
	}

}