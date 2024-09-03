package com.event.admin.ticket.processpayment.application.paybybill;

import com.event.admin.ticket.domain.Bill;
import com.event.admin.ticket.domain.Payment;
import com.event.admin.ticket.processpayment.application.shared.NotificationService;
import com.event.admin.ticket.processpayment.dataaccess.PaymentRepository;
import com.event.admin.ticket.processpayment.domain.*;
import org.springframework.stereotype.Service;

@Service
public class PayByBillUseCase {
    private final BillFactory billFactory;
    private final NotificationService notificationService;
    private final PaymentRepository paymentRepository;

    public PayByBillUseCase(BillFactory billFactory, NotificationService notificationService, PaymentRepository paymentRepository) {
        this.billFactory = billFactory;
        this.notificationService = notificationService;
        this.paymentRepository = paymentRepository;
    }


    public Payment createPayment(BillPaymentRequest billPaymentRequest) {
        var bill = billFactory.createBill(billPaymentRequest);
        notificationService.notifyBuyer(bill);
        notificationService.notifyOrganizer(billPaymentRequest.organizerCompanyName());
        var payment = createPaymentFor(bill);
        paymentRepository.updatePayment(payment);
        return payment;
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
