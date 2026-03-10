package id.ac.ui.cs.advprog.eshop.model;

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
        this.method = method;
        this.paymentData = paymentData;
        this.order = order;
        this.status = validatePayment() ? "SUCCESS" : "REJECTED";
    }

    public Payment(UUID id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.order = null;
        this.status = validatePayment() ? "SUCCESS" : "REJECTED";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private boolean validatePayment() {
        if ("VOUCHER_CODE".equals(method)) {
            return validateVoucherCode();
        } else if ("CASH_ON_DELIVERY".equals(method)) {
            return validateCashOnDelivery();
        }
        return false;
    }

    private boolean validateVoucherCode() {
        if (paymentData == null) {
            return false;
        }

        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode == null) {
            return false;
        }

        if (voucherCode.length() != 16) {
            return false;
        }

        if (!voucherCode.startsWith("ESHOP")) {
            return false;
        }

        int numericCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) {
                numericCount++;
            }
        }

        return numericCount == 8;
    }

    private boolean validateCashOnDelivery() {
        if (paymentData == null) {
            return false;
        }

        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");

        if (address == null || address.isEmpty()) {
            return false;
        }

        if (deliveryFee == null || deliveryFee.isEmpty()) {
            return false;
        }

        return true;
    }
}
