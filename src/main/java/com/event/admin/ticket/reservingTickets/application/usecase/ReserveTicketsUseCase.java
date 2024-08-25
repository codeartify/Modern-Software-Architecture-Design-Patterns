package com.event.admin.ticket.reservingTickets.application.usecase;

import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.presenter.PresentBookTicketsFailure;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.in.ReserveTickets;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.gateway.FindBooker;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.gateway.FindEvent;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.presenter.PresentBookTicketsSuccess;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.gateway.UpdateEvent;
import com.event.admin.ticket.reservingTickets.domain.Booker;
import com.event.admin.ticket.reservingTickets.domain.EventId;
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
            var eventId1 = new EventId(eventId);

            Booker booker = findBooker.findByUsernameOrThrow(bookerUsername);
            SelectedEvent event = findEvent.findByIdOrThrow(eventId1);

            event.bookTickets(ticketType, numberOfTickets, booker);

            this.updateEvent.withValue(event);

            presentSuccess.present(eventId1,numberOfTickets, bookerUsername);
        } catch (Exception e) {
            presentFailure.present(e);
        }
    }

}
