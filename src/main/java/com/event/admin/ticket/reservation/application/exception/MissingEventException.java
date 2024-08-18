package com.event.admin.ticket.reservation.application.exception;

public class MissingEventException extends RuntimeException {
    public MissingEventException(String eventNotFound) {
        super(eventNotFound);
    }
}
