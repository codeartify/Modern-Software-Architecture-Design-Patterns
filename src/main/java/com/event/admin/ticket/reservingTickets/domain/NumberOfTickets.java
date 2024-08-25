package com.event.admin.ticket.reservingTickets.domain;

public record NumberOfTickets(Integer value) {
    public NumberOfTickets {
        if (value <= 0) {
            throw new IllegalArgumentException("Number of tickets must be greater than 0");
        }
    }

}
