package com.event.admin.ticket.payment.application;

import com.event.admin.ticket.model.Payment;
import com.event.admin.ticket.model.Ticket;
import com.event.admin.ticket.payment.dataaccess.PaymentRepository;
import com.event.admin.ticket.payment.dataaccess.TicketRepository;
import com.event.admin.ticket.payment.domain.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayByCreditCardUseCase {
    private final TotalAmountFactory totalAmountFactory;
    private final NotificationService notificationService;
    private final TicketRepository ticketRepository;
    private final PaymentRepository paymentRepository;

    public PayByCreditCardUseCase(TotalAmountFactory totalAmountFactory, NotificationService notificationService, TicketRepository ticketRepository, PaymentRepository paymentRepository) {
        this.totalAmountFactory = totalAmountFactory;
        this.notificationService = notificationService;
        this.ticketRepository = ticketRepository;
        this.paymentRepository = paymentRepository;
    }

    public Payment createPayment(CreditCardPaymentRequest creditCardPaymentRequest) {
        var updatedTickets = addQRCodeTo(creditCardPaymentRequest.tickets());
        updatedTickets.forEach(updatedTicket -> notificationService.notifyPerTicket(updatedTicket, creditCardPaymentRequest.buyerName()));
        notificationService.notifyOrganizer(creditCardPaymentRequest.organizerCompanyName());
        var totalAmount = this.totalAmountFactory.calculateTotalAmountFor(updatedTickets, creditCardPaymentRequest.discountCode());
        var payment = createPaymentFor(totalAmount);
        paymentRepository.updatePayment(payment);
        return payment;
    }

    private List<Ticket> addQRCodeTo(List<@Valid Ticket> tickets) {
        return tickets.stream()
                .map(ticket -> ticketRepository.updateQrCodeFor(ticket, QrCode.create()))
                .toList();
    }

    private Payment createPaymentFor(double totalAmount) {
        Payment payment = new Payment();
        payment.setAmount(totalAmount);
        payment.setPaymentMethod(PaymentMethod.CREDIT_CARD.name());
        payment.setDescription("Payment for tickets via credit card");
        payment.setSuccessful(true);
        return payment;
    }

}
