package com.event.admin.ticket.reservingTickets.domain.exception;

public class AllTicketsSoldException extends IllegalArgumentException {
    public AllTicketsSoldException(String message) {
        super(message);
    }

}
