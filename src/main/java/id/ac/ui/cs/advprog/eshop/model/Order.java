package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;

@Getter
public class Order {
    private UUID id;
    private List<Product> products;
    private long orderTime;
    private String author;
    private String status;

    public Order(UUID id, List<Product> products, long orderTime, String author) {
        
        this.id = id;
        this.orderTime = orderTime;
        this.author = author;
        this.status = OrderStatus.WAITING_PAYMENT.getValue();

        if (products.isEmpty()){
            throw new IllegalArgumentException();
        }else{
            this.products = products;
        }
    }

    public Order(UUID id, List<Product> products, long orderTime, String author, String status) {
        this(id, products, orderTime, author);
        this.setStatus(status);
    }

    public void setStatus(String status) {
        if (OrderStatus.contains(status)){
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

}