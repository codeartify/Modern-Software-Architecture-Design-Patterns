package com.event.admin.ticket.processpayment.domain;

public enum PaymentMethod {
    CREDIT_CARD, BILL;

    public static PaymentMethod toPaymentMethod(String paymentMethod) {
        return valueOf(paymentMethod.toUpperCase());
    }
}
