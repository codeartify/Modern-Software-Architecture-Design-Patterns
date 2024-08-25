package com.event.admin.ticket.payment.application;

import com.event.admin.ticket.model.*;
import com.event.admin.ticket.payment.domain.OrganizerCompanyName;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PayByCreditCardUseCase implements PaymentUseCase {
    private final JdbcTemplate jdbcTemplate;
    private final TotalAmountFactory totalAmountFactory;
    private final NotificationService notificationService;

    public PayByCreditCardUseCase(JdbcTemplate jdbcTemplate, TotalAmountFactory totalAmountFactory, NotificationService notificationService) {
        this.jdbcTemplate = jdbcTemplate;
        this.totalAmountFactory = totalAmountFactory;
        this.notificationService = notificationService;
    }

    @Override
    public Payment createPayment(PaymentRequest paymentRequest) {

        var organizerCompanyName = paymentRequest.getOrganizerCompanyName();


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

        notificationService.notifyOrganizer(new OrganizerCompanyName(organizerCompanyName));

        Payment payment = new Payment();

        payment.setAmount(this.totalAmountFactory.calculateTotalAmountFor(paymentRequest.getTickets(), paymentRequest.getDiscountCode()));
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setDescription("Payment for tickets via credit card");
        payment.setSuccessful(true);
        return payment;

    }
}
