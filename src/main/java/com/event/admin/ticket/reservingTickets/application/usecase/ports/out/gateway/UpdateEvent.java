package com.event.admin.ticket.reservingTickets.application.usecase.ports.out.gateway;

import com.event.admin.ticket.reservingTickets.domain.SelectedEvent;

public interface UpdateEvent {
    SelectedEvent withValue(SelectedEvent event);
}
