package com.event.admin.ticket.reservation.application.exception;

public class MissingBookerException extends RuntimeException {
    public MissingBookerException(String bookerNotFound) {
        super(bookerNotFound);
    }
}
