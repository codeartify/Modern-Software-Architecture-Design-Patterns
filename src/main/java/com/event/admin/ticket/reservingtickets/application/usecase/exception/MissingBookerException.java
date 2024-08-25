package com.event.admin.ticket.reservingtickets.application.usecase.exception;

public class MissingBookerException extends RuntimeException {
    public MissingBookerException(String bookerNotFound) {
        super(bookerNotFound);
    }
}
