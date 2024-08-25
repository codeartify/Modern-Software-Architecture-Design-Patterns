package com.event.admin.ticket.reservingtickets.application.usecase.exception;

public class MissingEventException extends RuntimeException {
    public MissingEventException(String eventNotFound) {
        super(eventNotFound);
    }
}
