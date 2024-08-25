package com.event.admin.ticket.reservingTickets.application.usecase;

import com.event.admin.ticket.reservingTickets.application.usecase.exception.*;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.presenter.PresentBookTicketsFailure;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.in.ReserveTickets;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.gateway.FindBooker;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.gateway.FindEvent;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.presenter.PresentBookTicketsSuccess;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.gateway.UpdateEvent;
import com.event.admin.ticket.reservingTickets.domain.Booker;
import com.event.admin.ticket.reservingTickets.domain.Event2;
import com.event.admin.ticket.reservingTickets.domain.TicketsLeft;
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
            Event2 event = this.findEvent.findById(eventId).orElseThrow(() -> new MissingEventException("Event not found"));
            TicketsLeft ticketsLeft = event.getTicketsLeft();

            if (ticketsLeft.noTicketsLeft()) {
                throw new AllTicketsSoldException("No tickets left for the event");
            }

            if (event.exceedsNumberOfTicketsPerBooker(numberOfTickets)) {
                throw new NumberOfTicketsPerBuyerExceededException("Cannot reserve more tickets than allowed per buyer");
            }

            if (numberOfTickets > ticketsLeft.numberOfTicketsLeftForType(ticketType)) {
                throw new TooFewTicketsOfTypeLeftException("Not enough tickets of type " + ticketType + " left for the event");
            }

            ticketsLeft.markTicketsAsReserved(numberOfTickets, ticketType, booker);
            event.updateTicketsLeft(ticketsLeft);

            this.updateEvent.withValue(event);

            presentSuccess.present(eventId, numberOfTickets, bookerUsername);
        } catch (Exception e) {
            presentFailure.present(e);
        }
    }

}
