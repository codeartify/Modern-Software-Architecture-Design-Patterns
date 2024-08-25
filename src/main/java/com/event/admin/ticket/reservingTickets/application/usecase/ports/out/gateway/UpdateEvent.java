package com.event.admin.ticket.reservingTickets.application.usecase.ports.out.gateway;

import com.event.admin.ticket.reservingTickets.domain.Event2;

public interface UpdateEvent {
    Event2 withValue(Event2 event);
}
