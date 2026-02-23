package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setProductName("Test Product");
        product.setProductQuantity(10);
    }

    @Test
    void testCreateProduct() {
        when(productRepository.create(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.create(product);

        assertNotNull(createdProduct);
        assertEquals(product.getProductId(), createdProduct.getProductId());
        assertEquals("Test Product", createdProduct.getProductName());
        assertEquals(10, createdProduct.getProductQuantity());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testEditProductSuccess() {
        String updatedName = "Updated Product";
        int updatedQuantity = 20;

        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName(updatedName);
        updatedProduct.setProductQuantity(updatedQuantity);

        when(productRepository.findByProductId(product.getProductId())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.edit(product, updatedName, updatedQuantity);

        assertNotNull(result);
        assertEquals(updatedName, result.getProductName());
        assertEquals(updatedQuantity, result.getProductQuantity());
        verify(productRepository, times(1)).findByProductId(product.getProductId());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testEditProductNotFound() {
        String updatedName = "Updated Product";
        int updatedQuantity = 20;

        when(productRepository.findByProductId(product.getProductId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.edit(product, updatedName, updatedQuantity);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findByProductId(product.getProductId());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testFindAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        Product product2 = new Product();
        product2.setProductId(UUID.randomUUID());
        product2.setProductName("Product 2");
        product2.setProductQuantity(15);
        productList.add(product2);

        Iterator<Product> iterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Product", result.get(0).getProductName());
        assertEquals("Product 2", result.get(1).getProductName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindAllProductsEmpty() {
        List<Product> emptyList = new ArrayList<>();
        Iterator<Product> iterator = emptyList.iterator();

        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findAll();
    }
}
