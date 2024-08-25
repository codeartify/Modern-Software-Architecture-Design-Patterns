package com.event.admin.ticket.reservingTickets.application.usecase.ports.out.presenter;

import com.event.admin.ticket.reservingTickets.domain.EventId;

public interface PresentBookTicketsSuccess {
    void present(EventId eventId, int numberOfTickets, String bookerUsername);
}
