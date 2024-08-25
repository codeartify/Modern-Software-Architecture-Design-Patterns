package com.event.admin.ticket.reservingTickets.application.usecase.exception;

public class TooFewTicketsOfTypeLeftException extends IllegalArgumentException {
    public TooFewTicketsOfTypeLeftException(String message) {
        super(message);
    }
}
