package com.event.admin.ticket.payment;

import com.event.admin.ticket.model.*;
import com.event.admin.ticket.payment.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;

public class PayByBillUseCase implements PaymentUseCase {
    private final JdbcTemplate jdbcTemplate;
    private final OrganizerRepository organizerRepository;
    private final BillFactory billFactory;
    private final NotificationService notificationService;

    public PayByBillUseCase(JdbcTemplate jdbcTemplate, OrganizerRepository organizerRepository, BillFactory billFactory, NotificationService notificationService) {
        this.jdbcTemplate = jdbcTemplate;
        this.organizerRepository = organizerRepository;
        this.billFactory = billFactory;
        this.notificationService = notificationService;
    }

    @Override
    public Payment createPayment(PaymentRequest paymentRequest) {
        var organizerCompanyName = new OrganizerCompanyName(paymentRequest.getOrganizerCompanyName());
        var buyerCompanyName = new BuyerCompanyName(paymentRequest.getBuyerCompanyName());
        var buyerName = new BuyerName(paymentRequest.getBuyerName());
        var iban = new Iban(paymentRequest.getIban());
        var billDescription = new BillDescription(paymentRequest.getBillDescription());

        var bill = billFactory.createBill(paymentRequest, buyerCompanyName, buyerName, iban, billDescription, organizerCompanyName);
        notificationService.notifyBuyer(bill);
        notificationService.notifyOrganizer(organizerCompanyName);
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

}
