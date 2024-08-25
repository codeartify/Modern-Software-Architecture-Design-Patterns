package com.event.admin.ticket.reservingtickets.domain.exception;

public class AllTicketsSoldException extends IllegalArgumentException {
    public AllTicketsSoldException(String message) {
        super(message);
    }

}
