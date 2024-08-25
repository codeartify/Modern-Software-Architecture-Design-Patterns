package com.event.admin.ticket.reservingTickets.application.usecase.exception;

public class MissingBookerException extends RuntimeException {
    public MissingBookerException(String bookerNotFound) {
        super(bookerNotFound);
    }
}
