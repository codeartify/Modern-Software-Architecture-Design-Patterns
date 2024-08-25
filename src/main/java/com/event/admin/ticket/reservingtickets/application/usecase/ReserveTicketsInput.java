package com.event.admin.ticket.reservingtickets.application.usecase;

import com.event.admin.ticket.reservingtickets.domain.BookerUsername;
import com.event.admin.ticket.reservingtickets.domain.EventId;
import com.event.admin.ticket.reservingtickets.domain.NumberOfTickets;
import com.event.admin.ticket.reservingtickets.domain.TicketType;

public record ReserveTicketsInput(
        BookerUsername bookerUsername,
        EventId eventId,
        NumberOfTickets numberOfTickets,
        TicketType ticketType) {

    public static ReserveTicketsInput withValid(Long eventId, String ticketType, int numberOfTickets, String bookerUsername) {
        return new ReserveTicketsInput(new BookerUsername(bookerUsername), new EventId(eventId), new NumberOfTickets(numberOfTickets), TicketType.getValueOf(ticketType));
    }
}
