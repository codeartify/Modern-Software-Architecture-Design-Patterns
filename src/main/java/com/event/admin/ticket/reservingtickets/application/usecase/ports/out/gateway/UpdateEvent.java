package com.event.admin.ticket.reservingtickets.application.usecase.ports.out.gateway;

import com.event.admin.ticket.reservingtickets.domain.SelectedEvent;

public interface UpdateEvent {
    SelectedEvent withValue(SelectedEvent event);
}
