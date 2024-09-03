package com.event.admin.ticket.reservingtickets.application.usecase;

import com.event.admin.ticket.reservingtickets.application.usecase.ports.in.ReserveTickets;
import com.event.admin.ticket.reservingtickets.application.usecase.ports.out.gateway.FindBooker;
import com.event.admin.ticket.reservingtickets.application.usecase.ports.out.gateway.FindEvent;
import com.event.admin.ticket.reservingtickets.application.usecase.ports.out.gateway.UpdateEvent;
import com.event.admin.ticket.reservingtickets.application.usecase.ports.out.presenter.PresentBookTicketsFailure;
import com.event.admin.ticket.reservingtickets.application.usecase.ports.out.presenter.PresentBookTicketsSuccess;
import com.event.admin.ticket.reservingtickets.domain.Booker;
import com.event.admin.ticket.reservingtickets.domain.SelectedEvent;
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

            var reserveTicketsOutput = toOutput(reserveTicketsInput, event, booker);
            presentSuccess.present(reserveTicketsOutput);
        } catch (Exception e) {
            presentFailure.present(e);
        }
    }

    private static ReserveTicketsOutput toOutput(ReserveTicketsInput reserveTicketsInput, SelectedEvent event, Booker booker) {
        return new ReserveTicketsOutput(
                event.getId(),
                reserveTicketsInput.numberOfTickets(),
                booker.username());
    }

}
