package com.event.admin.ticket.reservation.application.ports.out;

public interface PresentBookTicketsSuccess {
    void present(Long eventId, int numberOfTickets, String bookerUsername);
}
