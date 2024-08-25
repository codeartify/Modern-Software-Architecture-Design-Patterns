package com.event.admin.ticket.reservingTickets.domain.exception;

public class TooFewTicketsOfTypeLeftException extends IllegalArgumentException {
    public TooFewTicketsOfTypeLeftException(String message) {
        super(message);
    }
}
