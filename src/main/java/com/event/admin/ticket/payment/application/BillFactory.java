package com.event.admin.ticket.payment.application;

import com.event.admin.ticket.model.Bill;
import com.event.admin.ticket.model.Ticket;
import com.event.admin.ticket.payment.domain.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class BillFactory {
    private final TotalAmountFactory totalAmountFactory;

    public Bill createBill(BuyerCompanyName buyerCompanyName, BuyerName buyerName, Iban iban, BillDescription billDescription, OrganizerCompanyName organizerCompanyName, List<@Valid Ticket> tickets, DiscountCode discountCode) {
        Bill bill = new Bill();
        bill.setBuyerCompanyName(buyerCompanyName.value());
        bill.setBuyerName(buyerName.value());
        bill.setAmount(this.totalAmountFactory.getTotalAmountWithFee(tickets, discountCode));
        bill.setIban(iban.value());
        bill.setDescription(billDescription.value());
        bill.setOrganizerCompanyName(organizerCompanyName.value());
        bill.setCreationDate(LocalDate.now());
        bill.setPaid(false);
        return bill;
    }
}
