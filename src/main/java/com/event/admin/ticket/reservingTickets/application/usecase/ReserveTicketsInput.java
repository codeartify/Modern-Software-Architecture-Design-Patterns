package com.event.admin.ticket.reservingTickets.application.usecase;

import com.event.admin.ticket.reservingTickets.domain.BookerUsername;
import com.event.admin.ticket.reservingTickets.domain.EventId;
import com.event.admin.ticket.reservingTickets.domain.NumberOfTickets;
import com.event.admin.ticket.reservingTickets.domain.TicketType;

public record ReserveTicketsInput(
        BookerUsername bookerUsername,
        EventId eventId,
        NumberOfTickets numberOfTickets,
        TicketType ticketType) {

    public static ReserveTicketsInput withValid(Long eventId, String ticketType, int numberOfTickets, String bookerUsername) {
        return new ReserveTicketsInput(new BookerUsername(bookerUsername), new EventId(eventId), new NumberOfTickets(numberOfTickets), TicketType.getValueOf(ticketType));
    }
}
