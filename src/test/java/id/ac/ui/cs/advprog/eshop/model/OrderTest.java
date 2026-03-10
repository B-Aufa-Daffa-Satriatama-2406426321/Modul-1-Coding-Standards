package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private List<Product> products;

    @BeforeEach
    void setUp() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId(uuid1);
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        Product product2 = new Product();
        product2.setProductId(uuid2);
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);
        this.products.add(product1);
        this.products.add(product2);

    }

    @Test
    void testCreateOrderEmptyProducts() {
        this.products.clear();

        assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order(UUID.randomUUID(), this.products, System.currentTimeMillis(), "John Doe");
        });
    }

    @Test
    void testCreateOrderDefaultStatus() {
        UUID uuid3 = UUID.randomUUID();
        long time = System.currentTimeMillis();
        Order order = new Order(uuid3, this.products, time, "Safira Sudrajat");
        
        assertSame(this.products, order.getProducts());
        assertEquals(2, order.getProducts().size());
        assertEquals("Sampo Cap Bambang", order.getProducts().get(0).getProductName());
        assertEquals("Sabun Cap Usep", order.getProducts().get(1).getProductName());

        assertEquals(uuid3, order.getId());
        assertEquals(time, order.getOrderTime());
        assertEquals("Safira Sudrajat", order.getAuthor());
        assertEquals("WAITING_PAYMENT", order.getStatus());


    }

    @Test
    void testCreateOrderSuccessStatus(){
        UUID uuid4 = UUID.randomUUID();
        Long time2 = System.currentTimeMillis();
        Order order = new Order(uuid4, this.products, time2, "Safira Sudrajat", OrderStatus.SUCCESS.getValue());

        assertEquals("SUCCESS", order.getStatus());
    }

    @Test
    void testCreateOrderInvalidStatus() {
        UUID uuid4 = UUID.randomUUID();
        Long time2 = System.currentTimeMillis();
        assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order(uuid4, this.products, time2, "Safira Sudrajat", "MEOW");
        });
    }

    @Test
    void testSetStatusToCancelled(){
        UUID uuid4 = UUID.randomUUID();
        Long time2 = System.currentTimeMillis();
        Order order = new Order(uuid4, this.products, time2, "Safira Sudrajat");

        order.setStatus("CANCELLED");
        assertEquals("CANCELLED", order.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus(){
        UUID uuid4 = UUID.randomUUID();
        Long time2 = System.currentTimeMillis();
        Order order = new Order(uuid4, this.products, time2, "Safira Sudrajat");

        assertThrows(IllegalArgumentException.class, () -> order.setStatus("MEOW"));
    }

}