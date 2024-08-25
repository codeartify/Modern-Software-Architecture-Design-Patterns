package com.event.admin.ticket.reservingTickets.adapter.in;

import com.event.admin.ticket.reservingTickets.adapter.out.presenter.ReserveTicketsForEventPresenter;
import com.event.admin.ticket.reservingTickets.application.usecase.ReserveTicketsInput;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.in.ReserveTickets;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReserveTicketsController {

    private final ReserveTickets reserveTickets;

    @PostMapping("/events/{id}/tickets")
    @Transactional
    public ResponseEntity<Object> reserveTicketsForEvent(
            @PathVariable("id") @NotNull Long eventId,
            @Valid @RequestBody ReserveTicketsRequest reserveTicketsRequest) {
        log.info("Reserving tickets for event with ID: {}", eventId);
        log.info("Reserve tickets request: {}", reserveTicketsRequest);

        var presenter = new ReserveTicketsForEventPresenter();

        var ticketType = reserveTicketsRequest.getTicketType();
        var numberOfTickets = reserveTicketsRequest.getNumberOfTickets();
        var bookerUsername = reserveTicketsRequest.getBookerUsername();

        log.info("Reserving {} tickets of type {} for user {}", numberOfTickets, ticketType, bookerUsername);

        var reserveTicketsInput = ReserveTicketsInput.withValid(eventId, ticketType, numberOfTickets, bookerUsername);
        reserveTickets.execute(reserveTicketsInput, presenter, presenter);

        if (presenter.hasError()) {
            log.error("Error reserving tickets: {}", presenter.getError());
            return presenter.getError();
        } else {
            log.info("Successfully reserved tickets: {}", presenter.getSuccess());
            return presenter.getSuccess();
        }
    }
}
