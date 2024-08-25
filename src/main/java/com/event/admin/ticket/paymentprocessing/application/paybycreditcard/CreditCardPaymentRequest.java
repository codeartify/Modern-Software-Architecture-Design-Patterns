package com.event.admin.ticket.paymentprocessing.application.paybycreditcard;

import com.event.admin.ticket.model.Ticket;
import com.event.admin.ticket.paymentprocessing.domain.BuyerName;
import com.event.admin.ticket.paymentprocessing.domain.DiscountCode;
import com.event.admin.ticket.paymentprocessing.domain.OrganizerCompanyName;
import jakarta.validation.Valid;

import java.util.List;

public record CreditCardPaymentRequest(
        List<@Valid Ticket> tickets,
        BuyerName buyerName,
        OrganizerCompanyName organizerCompanyName,
        DiscountCode discountCode) {
}
