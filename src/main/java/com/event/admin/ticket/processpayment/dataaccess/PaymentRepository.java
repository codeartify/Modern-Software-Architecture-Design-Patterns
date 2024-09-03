package com.event.admin.ticket.processpayment.dataaccess;

import com.event.admin.ticket.domain.Payment;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PaymentRepository {
    private final JdbcTemplate jdbcTemplate;

    public void updatePayment(Payment payment) {
        String query = "INSERT INTO payment (amount, payment_method, description, successful) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(query, payment.getAmount(), payment.getPaymentMethod(), payment.getDescription(), payment.isSuccessful());
    }
}
