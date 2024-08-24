package com.event.admin.ticket.payment;

import org.springframework.jdbc.core.JdbcTemplate;

public class BillPaymentFactory extends PaymentFactory {
    public BillPaymentFactory(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }
}
