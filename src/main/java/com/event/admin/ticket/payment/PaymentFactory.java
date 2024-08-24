package com.event.admin.ticket.payment;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentFactory {
    protected JdbcTemplate jdbcTemplate;

    protected PaymentFactory(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static PaymentUseCase createPaymentFactory(JdbcTemplate jdbcTemplate, String paymentType) {
        if ("credit_card".equalsIgnoreCase(paymentType)) {
            return new PayByCreditCardUseCase(jdbcTemplate, new TotalAmountFactory(jdbcTemplate));
        } else if ("bill".equalsIgnoreCase(paymentType)) {
            return new PayByBillUseCase(jdbcTemplate, new TotalAmountFactory(jdbcTemplate));
        } else {
            throw new IllegalArgumentException("Invalid payment type.");
        }
    }


}
