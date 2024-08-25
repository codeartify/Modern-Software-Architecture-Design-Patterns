package com.event.admin.ticket.payment.application;

import com.event.admin.ticket.payment.dataaccess.DiscountCodeRepository;
import com.event.admin.ticket.payment.dataaccess.OrganizerRepository;
import com.event.admin.ticket.payment.domain.PaymentMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentUseCaseFactory {

    public static PaymentUseCase createPaymentUseCase(JdbcTemplate jdbcTemplate, PaymentMethod paymentMethod) {
        if (PaymentMethod.CREDIT_CARD.equals(paymentMethod)) {
            return new PayByCreditCardUseCase(jdbcTemplate,
                    new TotalAmountFactory(
                            new DiscountCodeRepository(jdbcTemplate)));
        } else if (PaymentMethod.BILL.equals(paymentMethod)) {
            return new PayByBillUseCase(jdbcTemplate,
                    new OrganizerRepository(jdbcTemplate),
                    new BillFactory(
                            new TotalAmountFactory(
                                    new DiscountCodeRepository(jdbcTemplate))),
                    new NotificationService(
                            jdbcTemplate,
                            new OrganizerRepository(jdbcTemplate)));
        } else {
            throw new IllegalArgumentException("Invalid payment type.");
        }
    }


}
