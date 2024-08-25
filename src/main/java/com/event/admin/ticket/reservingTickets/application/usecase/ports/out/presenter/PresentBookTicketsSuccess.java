package com.event.admin.ticket.reservingTickets.application.usecase.ports.out.presenter;

public interface PresentBookTicketsSuccess {
    void present(Long eventId, int numberOfTickets, String bookerUsername);
}
