package com.event.admin.ticket.payment.domain;

public record BuyerCompanyName(String value) {
    public BuyerCompanyName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Buyer company name must not be null or empty.");
        }
    }
}
