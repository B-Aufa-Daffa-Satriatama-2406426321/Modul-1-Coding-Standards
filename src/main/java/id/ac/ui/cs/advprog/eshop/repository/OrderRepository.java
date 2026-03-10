package id.ac.ui.cs.advprog.eshop.repository;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

import id.ac.ui.cs.advprog.eshop.model.*;
import java.util.UUID;

@Repository
public class OrderRepository {
    
    private List<Order> orderData = new ArrayList<>();

    public Order save(Order order){
        int i = 0;
        for (Order savedOrder : orderData){
            if (savedOrder.getId().equals(order.getId())) {
                orderData.remove(i);
                orderData.add(i, order);
                return order;
            }
            i +=1;
        }

        orderData.add(order);
        return order;
    }

    public Order findById(UUID id){
        for (Order savedOrder : orderData) {
            if (savedOrder.getId().equals(id)){
                return savedOrder;
            }
        }

        return null;
    }

    public List<Order> findAllByAuthor(String author){
        List<Order> result = new ArrayList<>();
        for (Order savedOrder : orderData) {
            if (savedOrder.getAuthor().equals(author)) {
                result.add(savedOrder);
            }
        }

        return result;
    }


}
