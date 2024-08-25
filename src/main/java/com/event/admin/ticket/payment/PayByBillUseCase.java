package com.event.admin.ticket.payment;

import com.event.admin.ticket.model.*;
import com.event.admin.ticket.payment.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;

public class PayByBillUseCase implements PaymentUseCase {
    private final JdbcTemplate jdbcTemplate;
    private final OrganizerRepository organizerRepository;
    private final BillFactory billFactory;

    public PayByBillUseCase(JdbcTemplate jdbcTemplate, OrganizerRepository organizerRepository, BillFactory billFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.organizerRepository = organizerRepository;
        this.billFactory = billFactory;
    }

    @Override
    public Payment createPayment(PaymentRequest paymentRequest) {
        var organizerCompanyName = new OrganizerCompanyName(paymentRequest.getOrganizerCompanyName());
        var buyerCompanyName = new BuyerCompanyName(paymentRequest.getBuyerCompanyName());
        var buyerName = new BuyerName(paymentRequest.getBuyerName());
        var iban = new Iban(paymentRequest.getIban());
        var billDescription = new BillDescription(paymentRequest.getBillDescription());

        var bill = billFactory.createBill(paymentRequest, buyerCompanyName, buyerName, iban, billDescription, organizerCompanyName);
        notifyBuyer(bill);
        notifyOrganizer(organizerCompanyName);
        return createPaymentFor(bill);
    }

    private static Payment createPaymentFor(Bill bill) {
        Payment payment = new Payment();
        payment.setAmount(bill.getAmount());
        payment.setPaymentMethod(PaymentMethod.BILL.name());
        payment.setDescription("Bill payment for tickets");
        payment.setSuccessful(true);
        return payment;
    }

    private void notifyOrganizer(OrganizerCompanyName organizerCompanyName) {
        Organizer organizer = organizerRepository.findByOrganizerCompanyName(organizerCompanyName);

        if (organizer != null) {
            var organizerNotification = Notification.createNotification(organizer.getContactName(), "New Ticket Sale", "A new ticket sale has been processed. Please check your event dashboard.");
            sendNotification(organizerNotification);
        }
    }

    private void notifyBuyer(Bill bill) {
        var buyerNotification = Notification.createNotification(bill.getBuyerName(), "New Bill Issued", "A new bill has been issued to your company. Please check your details:\n" + "Amount: " + bill.getAmount() + "\n" + "Description: " + bill.getDescription());
        sendNotification(buyerNotification);
    }

    private void sendNotification(Notification notification) {
        String query1 = "INSERT INTO notification (recipient, subject, message) VALUES (?, ?, ?)";
        this.jdbcTemplate.update(query1, notification.getRecipient(), notification.getSubject(), notification.getMessage());
    }

}
