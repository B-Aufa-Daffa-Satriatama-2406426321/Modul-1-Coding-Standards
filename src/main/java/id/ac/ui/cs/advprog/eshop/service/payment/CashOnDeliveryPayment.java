package id.ac.ui.cs.advprog.eshop.service.payment;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethodType;

import java.util.Map;

public class CashOnDeliveryPayment implements PaymentMethod {

    @Override
    public String getMethodName() {
        return PaymentMethodType.CASH_ON_DELIVERY.getValue();
    }

    @Override
    public boolean validate(Map<String, String> paymentData) {
        if (paymentData == null) {
            return false;
        }

        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");

        return isNotEmpty(address) && isNotEmpty(deliveryFee);
    }

    private boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
