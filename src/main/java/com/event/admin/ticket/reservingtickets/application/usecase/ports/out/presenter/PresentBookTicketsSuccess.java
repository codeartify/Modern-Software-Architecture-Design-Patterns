package com.event.admin.ticket.reservingtickets.application.usecase.ports.out.presenter;

import com.event.admin.ticket.reservingtickets.domain.BookerUsername;
import com.event.admin.ticket.reservingtickets.domain.EventId;
import com.event.admin.ticket.reservingtickets.domain.NumberOfTickets;

public interface PresentBookTicketsSuccess {
    void present(EventId eventId, NumberOfTickets numberOfTickets, BookerUsername bookerUsername);
}
