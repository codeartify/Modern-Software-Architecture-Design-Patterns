package com.event.admin.ticket.payment.application;

import com.event.admin.ticket.model.Bill;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class BillFactory {
    private final TotalAmountFactory totalAmountFactory;

    public Bill createBill(BillPaymentRequest billPaymentRequest) {
        Bill bill = new Bill();
        bill.setBuyerCompanyName(billPaymentRequest.buyerCompanyName().value());
        bill.setBuyerName(billPaymentRequest.buyerName().value());
        bill.setAmount(this.totalAmountFactory.getTotalAmountWithFee(billPaymentRequest.tickets(), billPaymentRequest.discountCode()));
        bill.setIban(billPaymentRequest.iban().value());
        bill.setDescription(billPaymentRequest.billDescription().value());
        bill.setOrganizerCompanyName(billPaymentRequest.organizerCompanyName().value());
        bill.setCreationDate(LocalDate.now());
        bill.setPaid(false);
        return bill;
    }
}
