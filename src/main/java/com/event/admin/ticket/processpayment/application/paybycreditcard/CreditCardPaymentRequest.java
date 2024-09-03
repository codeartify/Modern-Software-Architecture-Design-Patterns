package com.event.admin.ticket.processpayment.application.paybycreditcard;

import com.event.admin.ticket.domain.Ticket;
import com.event.admin.ticket.processpayment.domain.BuyerName;
import com.event.admin.ticket.processpayment.domain.DiscountCode;
import com.event.admin.ticket.processpayment.domain.OrganizerCompanyName;
import jakarta.validation.Valid;

import java.util.List;

public record CreditCardPaymentRequest(
        List<@Valid Ticket> tickets,
        BuyerName buyerName,
        OrganizerCompanyName organizerCompanyName,
        DiscountCode discountCode) {
}
