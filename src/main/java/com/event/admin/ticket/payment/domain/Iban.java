package com.event.admin.ticket.payment.domain;

public record Iban(String value) {

    public Iban {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("IBAN must not be null or empty.");
        }
    }
}
