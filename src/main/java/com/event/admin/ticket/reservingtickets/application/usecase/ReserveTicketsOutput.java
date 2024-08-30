package com.event.admin.ticket.reservingtickets.application.usecase;

import com.event.admin.ticket.reservingtickets.domain.BookerUsername;
import com.event.admin.ticket.reservingtickets.domain.EventId;
import com.event.admin.ticket.reservingtickets.domain.NumberOfTickets;

public record ReserveTicketsOutput(
        EventId eventId, 
        NumberOfTickets numberOfTickets, 
        BookerUsername bookerUsername) {
}