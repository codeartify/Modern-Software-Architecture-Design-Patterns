package com.event.admin.ticket.reservation.application.exception;

public class NumberOfTicketsPerBuyerExceededException extends IllegalArgumentException {
    public NumberOfTicketsPerBuyerExceededException(String message) {
        super(message);
    }
}
