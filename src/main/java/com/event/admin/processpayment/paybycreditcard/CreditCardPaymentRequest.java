package com.event.admin.processpayment.paybycreditcard;

import com.event.admin.domain.Ticket;
import com.event.admin.processpayment.domain.BuyerName;
import com.event.admin.processpayment.domain.DiscountCode;
import com.event.admin.processpayment.domain.OrganizerCompanyName;
import jakarta.validation.Valid;

import java.util.List;

public record CreditCardPaymentRequest(
        List<@Valid Ticket> tickets,
        BuyerName buyerName,
        OrganizerCompanyName organizerCompanyName,
        DiscountCode discountCode) {
}
