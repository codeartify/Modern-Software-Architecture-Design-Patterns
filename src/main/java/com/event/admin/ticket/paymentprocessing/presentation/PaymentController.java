package com.event.admin.ticket.paymentprocessing.presentation;

import com.event.admin.ticket.model.Payment;
import com.event.admin.ticket.model.PaymentRequest;
import com.event.admin.ticket.paymentprocessing.application.paybybill.BillPaymentRequest;
import com.event.admin.ticket.paymentprocessing.application.paybycreditcard.CreditCardPaymentRequest;
import com.event.admin.ticket.paymentprocessing.application.paybybill.PayByBillUseCase;
import com.event.admin.ticket.paymentprocessing.application.paybycreditcard.PayByCreditCardUseCase;
import com.event.admin.ticket.paymentprocessing.domain.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class PaymentController {
    private final PayByBillUseCase payByBillUseCase;
    private final PayByCreditCardUseCase payByCreditCardUseCase;

    @PostMapping("/tickets/payment")
    @Transactional
    public ResponseEntity<Payment> processPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        log.info("Processing payment...");
        log.info("Payment request: {}", paymentRequest);

        var paymentMethod = PaymentMethod.toPaymentMethod(paymentRequest.getPaymentMethod());

        if (PaymentMethod.BILL.equals(paymentMethod)) {
            var billPaymentRequest = toBillPaymentRequest(paymentRequest);
            var payment = payByBillUseCase.createPayment(billPaymentRequest);
            return ResponseEntity.ok(payment);
        }

        if (PaymentMethod.CREDIT_CARD.equals(paymentMethod)) {
            var creditCardPaymentRequest = toCreditCardPaymentRequest(paymentRequest);
            var payment = payByCreditCardUseCase.createPayment(creditCardPaymentRequest);
            return ResponseEntity.ok(payment);
        }

        throw new IllegalArgumentException("Invalid payment type.");

    }

    private static CreditCardPaymentRequest toCreditCardPaymentRequest(PaymentRequest paymentRequest) {
        var tickets = paymentRequest.getTickets();
        var organizerCompanyName = new OrganizerCompanyName(paymentRequest.getOrganizerCompanyName());
        var buyerName = new BuyerName(paymentRequest.getBuyerName());
        var discountCode = new DiscountCode(paymentRequest.getDiscountCode());

        return new CreditCardPaymentRequest(tickets, buyerName, organizerCompanyName, discountCode);
    }

    private static BillPaymentRequest toBillPaymentRequest(PaymentRequest paymentRequest) {
        var organizerCompanyName = new OrganizerCompanyName(paymentRequest.getOrganizerCompanyName());
        var buyerCompanyName = new BuyerCompanyName(paymentRequest.getBuyerCompanyName());
        var buyerName = new BuyerName(paymentRequest.getBuyerName());
        var iban = new Iban(paymentRequest.getIban());
        var billDescription = new BillDescription(paymentRequest.getBillDescription());
        var tickets = paymentRequest.getTickets();
        var discountCode = new DiscountCode(paymentRequest.getDiscountCode());

        return new BillPaymentRequest(tickets, buyerName, organizerCompanyName, discountCode, buyerCompanyName, iban, billDescription);
    }

}
