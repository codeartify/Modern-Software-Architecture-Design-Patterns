package com.event.admin.ticket.reservingTickets.application.usecase.exception;

public class NumberOfTicketsPerBuyerExceededException extends IllegalArgumentException {
    public NumberOfTicketsPerBuyerExceededException(String message) {
        super(message);
    }
}
