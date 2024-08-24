package com.event.admin.ticket.payment;

import com.event.admin.ticket.model.DiscountCode;
import com.event.admin.ticket.model.PaymentRequest;
import com.event.admin.ticket.model.Ticket;
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
            return new PayByCreditCardUseCase(jdbcTemplate);
        } else if ("bill".equalsIgnoreCase(paymentType)) {
            return new PayByBillUseCase(jdbcTemplate);
        } else {
            throw new IllegalArgumentException("Invalid payment type.");
        }
    }


    protected double calculateTotalAmount(PaymentRequest paymentRequest) {
        double totalAmount = 0;
        double discountTotal = 0;

        if (paymentRequest.getTickets().size() > 20) {
            throw new IllegalArgumentException("Cannot purchase more than 20 overall tickets at a time.");
        }

        for (Ticket ticket : paymentRequest.getTickets()) {
            double ticketPriceWithVAT = ticket.getPrice() * 1.20;
            totalAmount += ticketPriceWithVAT;

            if (paymentRequest.getDiscountCode() != null && !paymentRequest.getDiscountCode().isEmpty()) {
                String query = "SELECT * FROM discount_code WHERE code = ?";
                DiscountCode discount = this.jdbcTemplate.queryForObject(query, new Object[]{paymentRequest.getDiscountCode()}, (rs, _) -> {
                    DiscountCode discount1 = new DiscountCode();
                    discount1.setCode(rs.getString("code"));
                    discount1.setDiscountPercentage(rs.getDouble("discount_percentage"));
                    discount1.setApplicableTicketType(rs.getString("applicable_ticket_type"));
                    return discount1;
                });
                if (discount != null && (discount.getApplicableTicketType() == null || discount.getApplicableTicketType().equals(ticket.getType()))) {
                    discountTotal += ticketPriceWithVAT * discount.getDiscountPercentage() / 100;
                }
            }
        }

        int numberOfTickets = paymentRequest.getTickets().size();
        double groupDiscount;
        if (numberOfTickets >= 11) {
            groupDiscount = 0.30;
        } else if (numberOfTickets >= 6) {
            groupDiscount = 0.25;
        } else if (numberOfTickets >= 2) {
            groupDiscount = 0.20;
        } else {
            groupDiscount = 0;
        }
        discountTotal += totalAmount * groupDiscount;
        totalAmount -= discountTotal;

        double result;
        if (totalAmount > 40.00) {
            result = totalAmount * 0.025;
        } else {
            result = 0.99;
        }
        totalAmount += result;
        return totalAmount;
    }

}
