package com.event.admin.ticket.reservingTickets.domain.exception;

public class NumberOfTicketsPerBuyerExceededException extends IllegalArgumentException {
    public NumberOfTicketsPerBuyerExceededException(String message) {
        super(message);
    }
}
