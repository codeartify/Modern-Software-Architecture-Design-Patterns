package com.event.admin.ticket.reservingTickets.application.usecase.exception;

public class MissingEventException extends RuntimeException {
    public MissingEventException(String eventNotFound) {
        super(eventNotFound);
    }
}
