package com.event.admin.ticket.reservingTickets.application.usecase.ports.out.gateway;

import com.event.admin.ticket.reservingTickets.domain.Event2;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface FindEvent {
    Optional<Event2> findById(@NotNull Long eventId);
}
