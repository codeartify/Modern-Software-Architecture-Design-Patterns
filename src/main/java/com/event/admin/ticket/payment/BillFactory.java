package com.event.admin.ticket.payment;

import com.event.admin.ticket.model.Bill;
import com.event.admin.ticket.model.PaymentRequest;
import com.event.admin.ticket.payment.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class BillFactory {
    private final TotalAmountFactory totalAmountFactory;

    Bill createBill(PaymentRequest paymentRequest, BuyerCompanyName buyerCompanyName, BuyerName buyerName, Iban iban, BillDescription billDescription, OrganizerCompanyName organizerCompanyName) {
        Bill bill = new Bill();
        bill.setBuyerCompanyName(buyerCompanyName.value());
        bill.setBuyerName(buyerName.value());
        bill.setAmount(this.totalAmountFactory.getTotalAmountWithFee(paymentRequest.getTickets(), paymentRequest.getDiscountCode()));
        bill.setIban(iban.value());
        bill.setDescription(billDescription.value());
        bill.setOrganizerCompanyName(organizerCompanyName.value());
        bill.setCreationDate(LocalDate.now());
        bill.setPaid(false);
        return bill;
    }
}
