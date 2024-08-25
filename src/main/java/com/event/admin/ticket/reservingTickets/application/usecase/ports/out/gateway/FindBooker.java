package com.event.admin.ticket.reservingTickets.application.usecase.ports.out.gateway;

import com.event.admin.ticket.reservingTickets.application.usecase.exception.MissingBookerException;
import com.event.admin.ticket.reservingTickets.domain.Booker;
import com.event.admin.ticket.reservingTickets.domain.BookerUsername;

import java.util.Optional;

public interface FindBooker {
    Optional<Booker> findByUsername(BookerUsername bookerUsername);

    default Booker findByUsernameOrThrow(BookerUsername bookerUsername) {
        return findByUsername(bookerUsername).orElseThrow(() -> new MissingBookerException("Booker not found"));
    }
}
