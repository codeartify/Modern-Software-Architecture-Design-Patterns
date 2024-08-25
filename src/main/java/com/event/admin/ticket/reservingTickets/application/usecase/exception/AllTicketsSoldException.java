package com.event.admin.ticket.reservingTickets.application.usecase.exception;

public class AllTicketsSoldException extends IllegalArgumentException {
    public AllTicketsSoldException(String message) {
        super(message);
    }

}
