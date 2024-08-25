package com.event.admin.ticket.payment;

import com.event.admin.ticket.payment.domain.PaymentMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentUseCaseFactory {
    protected JdbcTemplate jdbcTemplate;

    protected PaymentUseCaseFactory(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static PaymentUseCase createPaymentUseCase(JdbcTemplate jdbcTemplate, PaymentMethod paymentMethod) {
        if (PaymentMethod.CREDIT_CARD.equals(paymentMethod)) {
            return new PayByCreditCardUseCase(jdbcTemplate, new TotalAmountFactory(jdbcTemplate));
        } else if (PaymentMethod.BILL.equals(paymentMethod)) {
            return new PayByBillUseCase(jdbcTemplate, new OrganizerRepository(jdbcTemplate), new BillFactory(new TotalAmountFactory(jdbcTemplate)), new NotificationService(jdbcTemplate, new OrganizerRepository(jdbcTemplate)));
        } else {
            throw new IllegalArgumentException("Invalid payment type.");
        }
    }


}
