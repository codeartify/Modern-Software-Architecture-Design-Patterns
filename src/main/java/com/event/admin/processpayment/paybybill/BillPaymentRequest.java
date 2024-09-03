package com.event.admin.processpayment.paybybill;

import com.event.admin.domain.Ticket;
import com.event.admin.processpayment.domain.*;
import com.event.admin.ticket.processpayment.domain.*;
import jakarta.validation.Valid;

import java.util.List;

public record BillPaymentRequest(
        List<@Valid Ticket> tickets,
        BuyerName buyerName,
        OrganizerCompanyName organizerCompanyName,
        DiscountCode discountCode,
        BuyerCompanyName buyerCompanyName,
        Iban iban,
        BillDescription billDescription
) {
}
