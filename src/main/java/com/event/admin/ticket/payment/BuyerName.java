package com.event.admin.ticket.payment;

public record BuyerName(String value) {

    public BuyerName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Buyer name must not be null or empty.");
        }
    }
}
