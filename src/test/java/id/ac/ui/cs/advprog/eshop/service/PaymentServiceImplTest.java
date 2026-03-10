package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Order order;
    private Payment payment;
    private UUID paymentId;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setProductName("Test Product");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order(UUID.randomUUID(), products, System.currentTimeMillis(), "John Doe");

        paymentId = UUID.randomUUID();
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        payment = new Payment(paymentId, "VOUCHER_CODE", paymentData, order);
    }

    @Test
    void testAddPaymentWithVoucherCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);

        assertNotNull(result);
        assertEquals("VOUCHER_CODE", result.getMethod());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentWithCashOnDelivery() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "123 Main Street");
        paymentData.put("deliveryFee", "10000");

        Payment codPayment = new Payment(UUID.randomUUID(), "CASH_ON_DELIVERY", paymentData, order);
        when(paymentRepository.save(any(Payment.class))).thenReturn(codPayment);

        Payment result = paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData);

        assertNotNull(result);
        assertEquals("CASH_ON_DELIVERY", result.getMethod());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusToSuccess() {
        payment.setStatus("SUCCESS");
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.setStatus(payment, "SUCCESS");

        assertEquals("SUCCESS", result.getStatus());
        assertEquals("SUCCESS", payment.getOrder().getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testSetStatusToRejected() {
        payment.setStatus("REJECTED");
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.setStatus(payment, "REJECTED");

        assertEquals("REJECTED", result.getStatus());
        assertEquals("FAILED", payment.getOrder().getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testGetPaymentById() {
        when(paymentRepository.findById(paymentId)).thenReturn(payment);

        Payment result = paymentService.getPayment(paymentId);

        assertNotNull(result);
        assertEquals(paymentId, result.getId());
        verify(paymentRepository, times(1)).findById(paymentId);
    }

    @Test
    void testGetPaymentByIdNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        when(paymentRepository.findById(nonExistentId)).thenReturn(null);

        Payment result = paymentService.getPayment(nonExistentId);

        assertNull(result);
        verify(paymentRepository, times(1)).findById(nonExistentId);
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        payments.add(payment);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("address", "123 Main Street");
        paymentData2.put("deliveryFee", "10000");
        Payment payment2 = new Payment(UUID.randomUUID(), "CASH_ON_DELIVERY", paymentData2, order);
        payments.add(payment2);

        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(2, result.size());
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPaymentsEmpty() {
        when(paymentRepository.findAll()).thenReturn(new ArrayList<>());

        List<Payment> result = paymentService.getAllPayments();

        assertTrue(result.isEmpty());
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void testAddPaymentWithInvalidVoucherCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "INVALIDCODE");

        // Payment constructor should set status to REJECTED for invalid voucher
        Payment rejectedPayment = new Payment(UUID.randomUUID(), "VOUCHER_CODE", paymentData, order);
        when(paymentRepository.save(any(Payment.class))).thenReturn(rejectedPayment);

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);

        assertNotNull(result);
        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testAddPaymentWithInvalidCashOnDelivery() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "");

        Payment rejectedPayment = new Payment(UUID.randomUUID(), "CASH_ON_DELIVERY", paymentData, order);
        when(paymentRepository.save(any(Payment.class))).thenReturn(rejectedPayment);

        Payment result = paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData);

        assertNotNull(result);
        assertEquals("REJECTED", result.getStatus());
    }
}
