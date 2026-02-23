package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import java.util.Iterator;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {
    
    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind(){
        Product product = new Product();
        UUID uuid = UUID.randomUUID();
        product.setProductId(uuid);
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(uuid, savedProduct.getProductId());
        assertEquals("Sampo Cap Bambang", savedProduct.getProductName());
        assertEquals(100, savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty(){
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct(){
        Product product1 = new Product();
        UUID uuid1 = UUID.randomUUID();
        product1.setProductId(uuid1);
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);

        productRepository.create(product1);

        Product product2 = new Product();
        UUID uuid2 = UUID.randomUUID();
        product2.setProductId(uuid2);
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(uuid1, savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(uuid2, savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEdit(){
        Product product = new Product();
        UUID uuid = UUID.randomUUID();
        product.setProductId(uuid);
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        productRepository.create(product);

        product.setProductName("Sampo Cap Bambang (Edited)");
        product.setProductQuantity(150);
        productRepository.save(product);

        Optional<Product> editedProduct = productRepository.findByProductId(uuid);
        assertTrue(editedProduct.isPresent());
        assertEquals("Sampo Cap Bambang (Edited)", editedProduct.get().getProductName());
        assertEquals(150, editedProduct.get().getProductQuantity());
    }

    @Test
    void testDelete(){
        Product product = new Product();
        UUID uuid = UUID.randomUUID();
        product.setProductId(uuid);
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        productRepository.create(product);

        productRepository.delete(product);

        Optional<Product> deletedProduct = productRepository.findByProductId(uuid);
        assertFalse(deletedProduct.isPresent());
    }

    @Test
    void testCreateInvalidProduct(){
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setProductName("");
        product.setProductQuantity(-10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productRepository.create(product);
        });

        String expectedMessage = "Invalid product name";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testSaveOrAddProduct(){
        Product product = new Product();
        UUID uuid = UUID.randomUUID();
        product.setProductId(uuid);
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        productRepository.save(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(uuid, savedProduct.getProductId());
    }

    @Test
    void testCreateError(){

        IllegalArgumentException exception0 = assertThrows(IllegalArgumentException.class, 
            () -> {
                    productRepository.create(null);
                });

        assertEquals("Product cannot be null", exception0.getMessage());

        Product product = new Product();

        
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> {
                    productRepository.create(product);
                });

        assertEquals("Invalid product name", exception.getMessage());




        Product product1 = new Product();

        

        product1.setProductQuantity(-1);
        product1.setProductName("A");
        product1.setProductName("ABA");

        IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class, 
            () -> {
                    productRepository.create(product1);
                });
                
        assertEquals("Quantity must be >= 0", exception3.getMessage());



    }

    @Test
    void testEdit2(){
        Product product = new Product();
        UUID uuid = UUID.randomUUID();
        product.setProductId(uuid);
        product.setProductQuantity(3);
        product.setProductName("ABA");

    }

    

}
