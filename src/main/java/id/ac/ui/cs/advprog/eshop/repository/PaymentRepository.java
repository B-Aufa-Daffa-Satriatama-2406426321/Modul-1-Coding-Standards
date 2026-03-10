package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class PaymentRepository {

    private Map<UUID, Payment> paymentData = new HashMap<>();

    public Payment save(Payment payment) {
        paymentData.put(payment.getId(), payment);
        return payment;
    }

    public Payment findById(UUID id) {
        return paymentData.get(id);
    }

    public List<Payment> findAll() {
        return new ArrayList<>(paymentData.values());
    }
}
