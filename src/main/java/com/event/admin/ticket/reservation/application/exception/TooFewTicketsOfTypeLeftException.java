package com.event.admin.ticket.reservation.application.exception;

public class TooFewTicketsOfTypeLeftException extends IllegalArgumentException {
    public TooFewTicketsOfTypeLeftException(String message) {
        super(message);
    }
}
