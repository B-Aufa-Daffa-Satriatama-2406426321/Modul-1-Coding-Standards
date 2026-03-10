package id.ac.ui.cs.advprog.eshop.service.payment;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethodType;

import java.util.HashMap;
import java.util.Map;

public class PaymentMethodFactory {

    private static final Map<String, PaymentMethod> paymentMethods = new HashMap<>();

    static {
        paymentMethods.put(PaymentMethodType.VOUCHER_CODE.getValue(), new VoucherCodePayment());
        paymentMethods.put(PaymentMethodType.CASH_ON_DELIVERY.getValue(), new CashOnDeliveryPayment());
    }

    private PaymentMethodFactory() {
        // private constructor to prevent instantiation
    }

    public static PaymentMethod getPaymentMethod(String method) {
        PaymentMethod paymentMethod = paymentMethods.get(method);
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Unknown payment method: " + method);
        }
        return paymentMethod;
    }

    public static void registerPaymentMethod(String methodName, PaymentMethod paymentMethod) {
        paymentMethods.put(methodName, paymentMethod);
    }
}
