package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
public class Order {
    private UUID id;
    private List<Product> products;
    private long orderTime;
    private String author;
    private String status;

    private static final List<String> VALID_STATUSES = Arrays.asList(
            "WAITING_PAYMENT", "FAILED", "CANCELLED", "SUCCESS"
    );

    public Order(UUID id, List<Product> products, long orderTime, String author) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Products cannot be null or empty");
        }

        this.id = id;
        this.products = products;
        this.orderTime = orderTime;
        this.author = author;
        this.status = "WAITING_PAYMENT"; // Default status
    }

    public Order(UUID id, List<Product> products, long orderTime, String author, String status) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Products cannot be null or empty");
        }

        this.id = id;
        this.products = products;
        this.orderTime = orderTime;
        this.author = author;

        if (!VALID_STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid status");
        }
        this.status = status;
    }

    public void setStatus(String status) {
        if (!VALID_STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid status");
        }
        this.status = status;
    }

}