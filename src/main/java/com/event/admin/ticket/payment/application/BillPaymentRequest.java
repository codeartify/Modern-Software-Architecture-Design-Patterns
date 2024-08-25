package com.event.admin.ticket.payment.application;

import com.event.admin.ticket.model.Ticket;
import com.event.admin.ticket.payment.domain.*;
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
