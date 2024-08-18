package com.event.admin.ticket.reservation.application.ports.out;

import com.event.admin.ticket.reservation.domain.Booker;

import java.util.Optional;

public interface FindBooker {
    Optional<Booker> findByUsername(String bookerUsername);

}
