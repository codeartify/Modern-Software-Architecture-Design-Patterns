package com.event.admin.ticket.reservation.application.ports.in;

import com.event.admin.ticket.reservation.adapter.out.presenter.PresentBookTicketsFailure;
import com.event.admin.ticket.reservation.application.ports.out.PresentBookTicketsSuccess;

public interface ReserveTickets {
    void execute(
            Long eventId,
            String ticketType,
            int numberOfTickets,
            String bookerUsername,
            PresentBookTicketsSuccess presentSuccess,
            PresentBookTicketsFailure presentFailure);

}
