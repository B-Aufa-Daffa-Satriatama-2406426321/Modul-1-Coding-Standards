package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;
import id.ac.ui.cs.advprog.eshop.model.Order;
import java.util.UUID;

public interface OrderService {
    
    public Order createOrder(Order order);

    public Order updateStatus(UUID orderId, String status);

    public Order findById(UUID orderId);

    public List<Order> findAllByAuthor(String author);
}
