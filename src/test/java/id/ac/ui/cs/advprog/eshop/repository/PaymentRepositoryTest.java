package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;
    private List<Payment> payments;
    private Order order;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setProductName("Test Product");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order(UUID.randomUUID(), products, System.currentTimeMillis(), "John Doe");

        payments = new ArrayList<>();

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment1 = new Payment(UUID.randomUUID(), "VOUCHER_CODE", paymentData1, order);
        payments.add(payment1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("address", "123 Main Street");
        paymentData2.put("deliveryFee", "10000");
        Payment payment2 = new Payment(UUID.randomUUID(), "CASH_ON_DELIVERY", paymentData2, order);
        payments.add(payment2);
    }

    @Test
    void testSavePayment() {
        Payment payment = payments.get(0);
        Payment result = paymentRepository.save(payment);

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getMethod(), result.getMethod());
        assertEquals(payment.getStatus(), result.getStatus());
    }

    @Test
    void testSavePaymentUpdate() {
        Payment payment = payments.get(0);
        paymentRepository.save(payment);

        //update status
        payment.setStatus("REJECTED");
        Payment result = paymentRepository.save(payment);

        assertEquals("REJECTED", result.getStatus());

        //verifikasi
        Payment findResult = paymentRepository.findById(payment.getId());
        assertEquals("REJECTED", findResult.getStatus());
    }

    @Test
    void testFindByIdIfFound() {
        Payment payment = payments.get(0);
        paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(payment.getId());

        assertNotNull(findResult);
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getStatus(), findResult.getStatus());
    }

    @Test
    void testFindByIdIfNotFound() {
        Payment payment = payments.get(0);
        paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(UUID.randomUUID());

        assertNull(findResult);
    }

    @Test
    void testFindAll() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        List<Payment> allPayments = paymentRepository.findAll();

        assertEquals(2, allPayments.size());
    }

    @Test
    void testFindAllEmpty() {
        List<Payment> allPayments = paymentRepository.findAll();

        assertTrue(allPayments.isEmpty());
    }

    @Test
    void testFindAllAfterMultipleSaves() {
        Payment payment1 = payments.get(0);
        Payment payment2 = payments.get(1);

        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        List<Payment> allPayments = paymentRepository.findAll();

        assertEquals(2, allPayments.size());
        assertTrue(allPayments.stream().anyMatch(p -> p.getId().equals(payment1.getId())));
        assertTrue(allPayments.stream().anyMatch(p -> p.getId().equals(payment2.getId())));
    }
}
