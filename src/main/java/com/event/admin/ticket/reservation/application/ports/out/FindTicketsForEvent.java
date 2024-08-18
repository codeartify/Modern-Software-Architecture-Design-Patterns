package com.event.admin.ticket.reservation.application.ports.out;

import com.event.admin.ticket.reservation.domain.TicketsLeft;

public interface FindTicketsForEvent {
    TicketsLeft findTicketsLeftFor(Long id);
}
