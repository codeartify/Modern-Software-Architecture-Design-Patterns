package com.event.admin.ticket.payment;

import org.springframework.jdbc.core.JdbcTemplate;

public class CreditCardPaymentFactory extends PaymentFactory {
    public CreditCardPaymentFactory(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }
}
