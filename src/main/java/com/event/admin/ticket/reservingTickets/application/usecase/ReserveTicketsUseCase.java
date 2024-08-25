package com.event.admin.ticket.reservingTickets.application.usecase;

import com.event.admin.ticket.reservingTickets.application.usecase.exception.*;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.presenter.PresentBookTicketsFailure;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.in.ReserveTickets;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.gateway.FindBooker;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.gateway.FindEvent;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.presenter.PresentBookTicketsSuccess;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.gateway.UpdateEvent;
import com.event.admin.ticket.reservingTickets.domain.Booker;
import com.event.admin.ticket.reservingTickets.domain.SelectedEvent;
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
    public void execute(Long eventId, String ticketType, int numberOfTickets, String bookerUsername, PresentBookTicketsSuccess presentSuccess, PresentBookTicketsFailure presentFailure) {
        try {
            Booker booker = this.findBooker.findByUsername(bookerUsername).orElseThrow(() -> new MissingBookerException("Booker not found"));
            SelectedEvent event = this.findEvent.findById(eventId).orElseThrow(() -> new MissingEventException("Event not found"));

            event.bookTickets(ticketType, numberOfTickets, booker);

            this.updateEvent.withValue(event);

            presentSuccess.present(eventId, numberOfTickets, bookerUsername);
        } catch (Exception e) {
            presentFailure.present(e);
        }
    }

}
