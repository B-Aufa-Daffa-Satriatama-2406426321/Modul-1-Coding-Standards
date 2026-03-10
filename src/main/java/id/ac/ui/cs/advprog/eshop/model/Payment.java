package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethodType;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.service.payment.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.service.payment.PaymentMethodFactory;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
public class Payment {
    private UUID id;
    private String method;
    private String status;
    private Map<String, String> paymentData;
    private Order order;

    public Payment(UUID id, String method, Map<String, String> paymentData, Order order) {
        this.id = id;
        this.paymentData = paymentData;
        this.order = order;

        if (!PaymentMethodType.contains(method)) {
            throw new IllegalArgumentException("Invalid payment method: " + method);
        }
        this.method = method;

        PaymentMethod validator = PaymentMethodFactory.getPaymentMethod(method);
        this.status = validator.validate(paymentData)
                ? PaymentStatus.SUCCESS.getValue()
                : PaymentStatus.REJECTED.getValue();
    }

    public Payment(UUID id, String method, Map<String, String> paymentData) {
        this(id, method, paymentData, null);
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
        }
    }
}
