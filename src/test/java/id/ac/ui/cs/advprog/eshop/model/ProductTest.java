package id.ac.ui.cs.advprog.eshop.model;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    Product product;
    UUID uuid = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        this.product = new Product();
        this.product.setProductId(uuid);
        this.product.setProductName("Sampo Cap Bambang");
        this.product.setProductQuantity(100);
    }

    @Test
    void testGetProductId() {
        assertEquals(uuid, product.getProductId());
    }

    @Test
    void testGetProductName() {
        assertEquals("Sampo Cap Bambang", product.getProductName());
    }

    @Test
    void testGetProductQuantity() {
        assertEquals(100, product.getProductQuantity());
    }


    // Negative scenario tests
    @Test
    void testGetProductNameNegative() {
        assertNotEquals("Sampo Cap Gajah", product.getProductName());
    }

    @Test
    void testGetProductQuantityNegative() {
        assertNotEquals(50, product.getProductQuantity());
    }

    

}
