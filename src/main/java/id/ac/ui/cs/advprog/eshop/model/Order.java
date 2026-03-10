package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class Order {
    private String id;
    private List<Product> products;
    private Long orderTime;
    private String author;
    private String status;

    private static final List<String> VALID_STATUSES = Arrays.asList(
            "WAITING_PAYMENT", "FAILED", "CANCELLED", "SUCCESS"
    );

    public Order(String id, List<Product> products, Long orderTime, String author) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Products cannot be null or empty");
        }

        this.id = id;
        this.products = products;
        this.orderTime = orderTime;
        this.author = author;
        this.status = "WAITING_PAYMENT"; // Default status
    }

    public Order(String id, List<Product> products, Long orderTime, String author, String status) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Products cannot be null or empty");
        }

        this.id = id;
        this.products = products;
        this.orderTime = orderTime;
        this.author = author;

        // Validate status, use default if invalid
        if (VALID_STATUSES.contains(status)) {
            this.status = status;
        } else {
            this.status = "WAITING_PAYMENT";
        }
    }

    public void setStatus(String status) {
        if (VALID_STATUSES.contains(status)) {
            this.status = status;
        }
        // If invalid status, keep previous status (reject the change)
    }
}