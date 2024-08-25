package com.event.admin.ticket.reservingTickets.application.usecase;

import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.presenter.PresentBookTicketsFailure;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.in.ReserveTickets;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.gateway.FindBooker;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.gateway.FindEvent;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.presenter.PresentBookTicketsSuccess;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.gateway.UpdateEvent;
import com.event.admin.ticket.reservingTickets.domain.*;
import org.springframework.stereotype.Service;

@Service
public class ReserveTicketsUseCase implements ReserveTickets {
    private final FindBooker findBooker;
    private final FindEvent findEvent;
    private final UpdateEvent updateEvent;

    public ReserveTicketsUseCase(FindBooker findBooker, FindEvent findEvent, UpdateEvent updateEvent) {
        this.findBooker = findBooker;
        this.findEvent = findEvent;
        this.updateEvent = updateEvent;
    }

    @Override
    public void execute(ReserveTicketsInput reserveTicketsInput, PresentBookTicketsSuccess presentSuccess, PresentBookTicketsFailure presentFailure) {
        try {
            Booker booker = findBooker.findByUsernameOrThrow(reserveTicketsInput.bookerUsername());
            SelectedEvent event = findEvent.findByIdOrThrow(reserveTicketsInput.eventId());

            event.bookTickets(booker, reserveTicketsInput.numberOfTickets(), reserveTicketsInput.ticketType());

            this.updateEvent.withValue(event);

            presentSuccess.present(reserveTicketsInput.eventId(), reserveTicketsInput.numberOfTickets(), reserveTicketsInput.bookerUsername());
        } catch (Exception e) {
            presentFailure.present(e);
        }
    }

}
