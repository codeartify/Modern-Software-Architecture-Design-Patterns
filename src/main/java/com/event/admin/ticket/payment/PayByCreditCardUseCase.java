package com.event.admin.ticket.payment;

import com.event.admin.ticket.model.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

public class PayByCreditCardUseCase implements PaymentUseCase {
    private final JdbcTemplate jdbcTemplate;
    private final TotalAmountFactory totalAmountFactory;

    public PayByCreditCardUseCase(JdbcTemplate jdbcTemplate, TotalAmountFactory totalAmountFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.totalAmountFactory = totalAmountFactory;
    }

    @Override
    public Payment createPayment(PaymentRequest paymentRequest) {
        Payment payment;
        payment = new Payment();
        payment.setAmount(this.totalAmountFactory.calculateTotalAmount(paymentRequest));
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());

        var organizerCompanyName = paymentRequest.getOrganizerCompanyName();
        payment.setDescription("Payment for tickets via credit card");
        payment.setSuccessful(true);

        for (Ticket ticket : paymentRequest.getTickets()) {
            String qrCodeUrl = "http://example.com/qr?ticket=" + UUID.randomUUID();
            ticket.setQrCode(qrCodeUrl);

            String query = "UPDATE ticket SET qr_code = ? WHERE id = ?";
            this.jdbcTemplate.update(query, qrCodeUrl, ticket.getId());

            // Send notification to the buyer
            Notification buyerNotification = new Notification();
            buyerNotification.setRecipient(paymentRequest.getBuyerName());
            buyerNotification.setSubject("Payment Successful");
            buyerNotification.setMessage("Your payment was successful. Here is your ticket:\n" + "Event: " + ticket.getEvent().getName() + "\n" + "Ticket Type: " + ticket.getType() + "\n" + "QR Code: " + qrCodeUrl);
            String query1 = "INSERT INTO notification (recipient, subject, message) VALUES (?, ?, ?)";
            this.jdbcTemplate.update(query1, buyerNotification.getRecipient(), buyerNotification.getSubject(), buyerNotification.getMessage());
        }

        String query = "SELECT * FROM organizer WHERE company_name = ?";
        Organizer organizer = this.jdbcTemplate.queryForObject(query, new Object[]{organizerCompanyName}, (rs, _) -> {
            Organizer organizer1 = new Organizer();
            organizer1.setId(rs.getLong("id"));
            organizer1.setCompanyName(rs.getString("company_name"));
            organizer1.setContactName(rs.getString("contact_name"));
            return organizer1;
        });

        if (organizer != null) {
            Notification organizerNotification = new Notification();
            organizerNotification.setRecipient(organizer.getContactName());
            organizerNotification.setSubject("New Ticket Sale");
            organizerNotification.setMessage("A new ticket sale has been processed. Please check your event dashboard.");
            String query1 = "INSERT INTO notification (recipient, subject, message) VALUES (?, ?, ?)";
            this.jdbcTemplate.update(query1, organizerNotification.getRecipient(), organizerNotification.getSubject(), organizerNotification.getMessage());
        }
        return payment;

    }
}
