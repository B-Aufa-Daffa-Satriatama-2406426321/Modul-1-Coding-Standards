package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    private List<Product> products;
    private Order order;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setProductName("Test Product");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order(UUID.randomUUID(), products, System.currentTimeMillis(), "John Doe");
    }

    @Test
    void testCreatePaymentWithValidData() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        UUID paymentId = UUID.randomUUID();
        Payment payment = new Payment(paymentId, "VOUCHER_CODE", paymentData, order);

        assertEquals(paymentId, payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertNotNull(payment.getOrder());
    }

    @Test
    void testPaymentGetters() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        UUID paymentId = UUID.randomUUID();
        Payment payment = new Payment(paymentId, "VOUCHER_CODE", paymentData, order);

        assertEquals(paymentId, payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals(paymentData, payment.getPaymentData());
        assertEquals(order, payment.getOrder());
    }

    @Test
    void testPaymentSetStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        UUID paymentId = UUID.randomUUID();
        Payment payment = new Payment(paymentId, "VOUCHER_CODE", paymentData, order);
        payment.setStatus("SUCCESS");

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testVoucherCodePaymentValidCode() {
        // 16 chars, start from ESHOP + 8 numeric
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment(UUID.randomUUID(), "VOUCHER_CODE", paymentData, order);

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testVoucherCodePaymentInvalidCodeTooShort() {
        // less than 16 char
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234");

        Payment payment = new Payment(UUID.randomUUID(), "VOUCHER_CODE", paymentData, order);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherCodePaymentInvalidCodeTooLong() {
        //more than 16 char
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC567890");

        Payment payment = new Payment(UUID.randomUUID(), "VOUCHER_CODE", paymentData, order);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherCodePaymentInvalidCodeNotStartWithEshop() {
        //not start with ESHOP
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ABCDE1234ABC5678");

        Payment payment = new Payment(UUID.randomUUID(), "VOUCHER_CODE", paymentData, order);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherCodePaymentInvalidCodeNotEnoughNumbers() {
        // not have 8 num
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOPABCDEFGH123");

        Payment payment = new Payment(UUID.randomUUID(), "VOUCHER_CODE", paymentData, order);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherCodePaymentNullCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", null);

        Payment payment = new Payment(UUID.randomUUID(), "VOUCHER_CODE", paymentData, order);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherCodePaymentEmptyCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "");

        Payment payment = new Payment(UUID.randomUUID(), "VOUCHER_CODE", paymentData, order);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherCodePaymentMissingVoucherCodeKey() {
        Map<String, String> paymentData = new HashMap<>();
        // No voucherCode key

        Payment payment = new Payment(UUID.randomUUID(), "VOUCHER_CODE", paymentData, order);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCashOnDeliveryPaymentValidData() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "123 Main Street, City");
        paymentData.put("deliveryFee", "10000");

        Payment payment = new Payment(UUID.randomUUID(), "CASH_ON_DELIVERY", paymentData, order);

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCashOnDeliveryPaymentEmptyAddress() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "10000");

        Payment payment = new Payment(UUID.randomUUID(), "CASH_ON_DELIVERY", paymentData, order);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCashOnDeliveryPaymentNullAddress() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", null);
        paymentData.put("deliveryFee", "10000");

        Payment payment = new Payment(UUID.randomUUID(), "CASH_ON_DELIVERY", paymentData, order);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCashOnDeliveryPaymentEmptyDeliveryFee() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "123 Main Street, City");
        paymentData.put("deliveryFee", "");

        Payment payment = new Payment(UUID.randomUUID(), "CASH_ON_DELIVERY", paymentData, order);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCashOnDeliveryPaymentNullDeliveryFee() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "123 Main Street, City");
        paymentData.put("deliveryFee", null);

        Payment payment = new Payment(UUID.randomUUID(), "CASH_ON_DELIVERY", paymentData, order);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCashOnDeliveryPaymentMissingAddressKey() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("deliveryFee", "10000");

        Payment payment = new Payment(UUID.randomUUID(), "CASH_ON_DELIVERY", paymentData, order);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCashOnDeliveryPaymentMissingDeliveryFeeKey() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "123 Main Street, City");

        Payment payment = new Payment(UUID.randomUUID(), "CASH_ON_DELIVERY", paymentData, order);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCashOnDeliveryPaymentBothFieldsEmpty() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "");

        Payment payment = new Payment(UUID.randomUUID(), "CASH_ON_DELIVERY", paymentData, order);

        assertEquals("REJECTED", payment.getStatus());
    }
}
