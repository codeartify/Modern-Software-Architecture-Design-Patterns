package com.event.admin.ticket.reservingTickets.domain;

public record BookerId(Long value) {
    public BookerId {
        if (value == null || value < 0) {
            throw new IllegalArgumentException("BookerId must be greater than 0");
        }
    }
}
