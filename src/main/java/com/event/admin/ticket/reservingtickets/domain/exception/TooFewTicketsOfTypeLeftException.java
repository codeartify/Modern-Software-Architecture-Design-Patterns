package com.event.admin.ticket.reservingtickets.domain.exception;

public class TooFewTicketsOfTypeLeftException extends IllegalArgumentException {
    public TooFewTicketsOfTypeLeftException(String message) {
        super(message);
    }
}
