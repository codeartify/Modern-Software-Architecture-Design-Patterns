package com.event.admin.ticket.payment.application;

import com.event.admin.ticket.model.*;
import com.event.admin.ticket.payment.dataaccess.OrganizerRepository;
import com.event.admin.ticket.payment.domain.OrganizerCompanyName;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PayByCreditCardUseCase implements PaymentUseCase {
    private final JdbcTemplate jdbcTemplate;
    private final TotalAmountFactory totalAmountFactory;

    public PayByCreditCardUseCase(JdbcTemplate jdbcTemplate, TotalAmountFactory totalAmountFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.totalAmountFactory = totalAmountFactory;
    }

    @Override
    public Payment createPayment(PaymentRequest paymentRequest) {
        Payment payment = new Payment();

        payment.setAmount(this.totalAmountFactory.calculateTotalAmountFor(paymentRequest.getTickets(), paymentRequest.getDiscountCode()));
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

        Organizer organizer = new OrganizerRepository(jdbcTemplate).findByOrganizerCompanyName(new OrganizerCompanyName(organizerCompanyName));

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
