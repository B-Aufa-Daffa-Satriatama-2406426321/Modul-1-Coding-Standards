package id.ac.ui.cs.advprog.eshop.enums;

import lombok.Getter;

@Getter
public enum PaymentMethodType {
    VOUCHER_CODE("VOUCHER_CODE"),
    CASH_ON_DELIVERY("CASH_ON_DELIVERY");

    private final String value;

    PaymentMethodType(String value) {
        this.value = value;
    }

    public static boolean contains(String method) {
        for (PaymentMethodType type : PaymentMethodType.values()) {
            if (type.getValue().equals(method)) {
                return true;
            }
        }
        return false;
    }
}
