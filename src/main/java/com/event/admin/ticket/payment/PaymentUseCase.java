package com.event.admin.ticket.payment;

import com.event.admin.ticket.model.Payment;
import com.event.admin.ticket.model.PaymentRequest;

public interface PaymentUseCase {
    Payment createPayment(PaymentRequest paymentRequest);
}
