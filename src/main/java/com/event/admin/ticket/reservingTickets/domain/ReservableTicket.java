package com.event.admin.ticket.reservingTickets.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public final class ReservableTicket {
    private BookerId bookerId;
    private final TicketType ticketType;

    public ReservableTicket(BookerId bookerId, TicketType ticketType) {
        this.bookerId = bookerId;
        this.ticketType = ticketType;
    }

    public void bookedBy(BookerId bookerId) {
        this.bookerId = bookerId;
    }

    public boolean canBeReserved() {
        return !isReserved();
    }

    public boolean isReserved() {
        return this.getBookerId() != null;
    }

    public boolean isOfType(TicketType ticketType) {
        return ticketType == getTicketType();
    }

    boolean isRequestedTicket(TicketType ticketType) {
        return isOfType(ticketType) && canBeReserved();
    }
}
