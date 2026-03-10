package id.ac.ui.cs.advprog.eshop.service.payment;

import java.util.Map;

public interface PaymentMethod {
    String getMethodName();
    boolean validate(Map<String, String> paymentData);
}
