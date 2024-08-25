package com.event.admin.ticket.payment.application;

import com.event.admin.ticket.model.Ticket;
import com.event.admin.ticket.payment.domain.BuyerName;
import com.event.admin.ticket.payment.domain.DiscountCode;
import com.event.admin.ticket.payment.domain.OrganizerCompanyName;
import jakarta.validation.Valid;

import java.util.List;

public record CreditCardPaymentRequest(
        List<@Valid Ticket> tickets,
        BuyerName buyerName,
        OrganizerCompanyName organizerCompanyName,
        DiscountCode discountCode) {
}
