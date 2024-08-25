package com.event.admin.ticket.reservingTickets.application.usecase.ports.out.presenter;

import com.event.admin.ticket.reservingTickets.domain.BookerUsername;
import com.event.admin.ticket.reservingTickets.domain.EventId;
import com.event.admin.ticket.reservingTickets.domain.NumberOfTickets;

public interface PresentBookTicketsSuccess {
    void present(EventId eventId, NumberOfTickets numberOfTickets, BookerUsername bookerUsername);
}
