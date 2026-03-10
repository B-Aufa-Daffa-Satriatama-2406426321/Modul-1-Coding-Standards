package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PaymentService {

    Payment addPayment(Order order, String method, Map<String, String> paymentData);

    Payment setStatus(Payment payment, String status);

    Payment getPayment(UUID paymentId);

    List<Payment> getAllPayments();
}
