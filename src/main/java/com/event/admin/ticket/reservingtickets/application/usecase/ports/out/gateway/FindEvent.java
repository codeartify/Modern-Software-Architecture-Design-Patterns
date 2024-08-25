package com.event.admin.ticket.reservingtickets.application.usecase.ports.out.gateway;

import com.event.admin.ticket.reservingtickets.application.usecase.exception.MissingEventException;
import com.event.admin.ticket.reservingtickets.domain.EventId;
import com.event.admin.ticket.reservingtickets.domain.SelectedEvent;

import java.util.Optional;

public interface FindEvent {
    Optional<SelectedEvent> findById(EventId eventId);

    default SelectedEvent findByIdOrThrow(EventId eventId) {
        return findById(eventId).orElseThrow(() -> new MissingEventException("Event not found"));
    }
}
