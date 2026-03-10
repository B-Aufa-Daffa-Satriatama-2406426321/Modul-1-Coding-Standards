package id.ac.ui.cs.advprog.eshop.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;

import static org.junit.jupiter.api.Assertions.*;

public class OrderRepositoryTest {
    OrderRepository orderRepository;
    List<Order> orders;

    @BeforeEach
    void setUp(){
        orderRepository = new OrderRepository();

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        UUID uuid = UUID.randomUUID();
        product1.setProductId(uuid);
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        orders = new ArrayList<>();
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();
        Order order1 = new Order(uuid1, products, System.currentTimeMillis(), "Safira Sudrajat");
        orders.add(order1);
        Order order2 = new Order(uuid2, products, System.currentTimeMillis(), "Safira Sudrajat");
        orders.add(order2);
        Order order3 = new Order(uuid3, products, System.currentTimeMillis(), "Bambang Sudrajat");
        orders.add(order3);



    }

    @Test
    void testSaveCreate(){
        Order order = orders.get(1);
        Order result = orderRepository.save(order);

        Order findResult = orderRepository.findById(orders.get(1).getId());
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getId(), findResult.getId());
        assertEquals(order.getOrderTime(), findResult.getOrderTime());
        assertEquals(order.getAuthor(), findResult.getAuthor());
        assertEquals(order.getStatus(), findResult.getStatus());
    }

    @Test
    void testSaveUpdate(){
        Order order = orders.get(1);
        

        Order newOrder = new Order(order.getId(), order.getProducts(), order.getOrderTime(),
            order.getAuthor(), OrderStatus.SUCCESS.getValue());
        
        Order result = orderRepository.save(order);

        Order findResult = orderRepository.findById(orders.get(1).getId());
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getId(), findResult.getId());
        assertEquals(order.getOrderTime(), findResult.getOrderTime());
        assertEquals(order.getAuthor(), findResult.getAuthor());
        assertEquals(order.getStatus(), findResult.getStatus());        
        
    }

    @Test
    void testFindByIdIfIdFound(){
        for (Order order : orders){
            orderRepository.save(order);
        }

        Order order = orders.get(1);
        Order findResult = orderRepository.findById(order.getId());
        assertEquals(order.getId(), findResult.getId());
        assertEquals(order.getOrderTime(), findResult.getOrderTime());
        assertEquals(order.getAuthor(), findResult.getAuthor());
        assertEquals(order.getStatus(), findResult.getStatus());            
    }

    @Test
    void testFindByIdIfIdNotFound(){
        for (Order order : orders){
            orderRepository.save(order);
        }

        UUID randomId = UUID.randomUUID();
        Order findResult = orderRepository.findById(randomId);
        assertNull(findResult);
    }

    @Test
    void testFindAllByAuthorIfAuthorCorrect(){
        for (Order order : orders){
            orderRepository.save(order);
        }
        
        List<Order> orderList = orderRepository.findAllByAuthor(
            orders.get(1).getAuthor().toLowerCase()
        );
        assertTrue(orderList.isEmpty());
    }

    
}
