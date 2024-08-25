package com.event.admin.ticket.reservingtickets.domain.exception;

public class NumberOfTicketsPerBuyerExceededException extends IllegalArgumentException {
    public NumberOfTicketsPerBuyerExceededException(String message) {
        super(message);
    }
}
