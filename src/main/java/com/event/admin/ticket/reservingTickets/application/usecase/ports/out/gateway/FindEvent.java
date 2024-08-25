package com.event.admin.ticket.reservingTickets.application.usecase.ports.out.gateway;

import com.event.admin.ticket.reservingTickets.domain.SelectedEvent;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface FindEvent {
    Optional<SelectedEvent> findById(@NotNull Long eventId);
}
