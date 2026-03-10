package id.ac.ui.cs.advprog.eshop.service.payment;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethodType;

import java.util.Map;

public class VoucherCodePayment implements PaymentMethod {

    private static final int VOUCHER_LENGTH = 16;
    private static final String VOUCHER_PREFIX = "ESHOP";
    private static final int REQUIRED_NUMERIC_COUNT = 8;

    @Override
    public String getMethodName() {
        return PaymentMethodType.VOUCHER_CODE.getValue();
    }

    @Override
    public boolean validate(Map<String, String> paymentData) {
        if (paymentData == null) {
            return false;
        }

        String voucherCode = paymentData.get("voucherCode");

        if (voucherCode == null) {
            return false;
        }

        if (voucherCode.length() != VOUCHER_LENGTH) {
            return false;
        }

        if (!voucherCode.startsWith(VOUCHER_PREFIX)) {
            return false;
        }

        long numericCount = voucherCode.chars()
                .filter(Character::isDigit)
                .count();

        return numericCount == REQUIRED_NUMERIC_COUNT;
    }
}
