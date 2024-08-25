package com.event.admin.ticket.reservingtickets.domain;

import com.event.admin.ticket.reservingtickets.domain.exception.NumberOfTicketsPerBuyerExceededException;
import lombok.Getter;
import lombok.ToString;

@DDDAggregate
@ToString
@Getter
public class SelectedEvent {
    private final EventId id;
    private final TicketsLeft ticketsLeft;
    private final NumberOfTickets numberOfTicketsPerBooker;

    public SelectedEvent(EventId id, TicketsLeft ticketsLeft, NumberOfTickets numberOfTickets) {
        this.id = id;
        this.numberOfTicketsPerBooker = numberOfTickets;
        this.ticketsLeft = ticketsLeft;
    }

    public void bookTickets(Booker booker, NumberOfTickets requestedNumberOfTickets, TicketType ticketType) {
        if (exceedsNumberOfTicketsPerBooker(requestedNumberOfTickets)) {
            throw new NumberOfTicketsPerBuyerExceededException("Cannot reserve more tickets than allowed per buyer");
        }

        ticketsLeft.markTicketsAsReserved(booker, ticketType, requestedNumberOfTickets);
    }

    public boolean exceedsNumberOfTicketsPerBooker(NumberOfTickets requestedNumberOfTickets) {
        return numberOfTicketsPerBooker.value() < requestedNumberOfTickets.value();
    }
}

