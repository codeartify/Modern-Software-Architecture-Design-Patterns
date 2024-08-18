package com.event.admin.ticket.reservation.application.ports.out;

import com.event.admin.ticket.reservation.domain.Event2;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface FindEvent {
    Optional<Event2> findById(@NotNull Long eventId);
}
