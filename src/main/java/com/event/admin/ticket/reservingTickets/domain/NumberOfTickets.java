package com.event.admin.ticket.reservingTickets.domain;

public class NumberOfTickets {
    private Integer value;

    public NumberOfTickets(Integer value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Number of tickets must be greater than 0");
        }
        this.value = value;
    }

    public void decrement() {
        this.value--;
    }

    public boolean isZero() {
        return value == 0;
    }

    public int value() {
        return value;
    }
}
