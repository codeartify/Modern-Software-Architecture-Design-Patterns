package com.event.admin.ticket.reservingTickets.application.usecase.ports.out.gateway;

import com.event.admin.ticket.reservingTickets.domain.Booker;

import java.util.Optional;

public interface FindBooker {
    Optional<Booker> findByUsername(String bookerUsername);

}
