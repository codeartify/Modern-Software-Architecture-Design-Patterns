package com.event.admin.ticket.reservingTickets.domain;

import com.event.admin.ticket.reservingTickets.domain.exception.NumberOfTicketsPerBuyerExceededException;
import lombok.ToString;

@DDDAggregate
@ToString
public class SelectedEvent {
    private final EventId id;
    private final TicketsLeft ticketsLeft;
    private final NumberOfTickets numberOfTicketsPerBooker;

    public SelectedEvent(EventId id, TicketsLeft ticketsLeft, NumberOfTickets numberOfTickets) {
        this.id = id;
        this.numberOfTicketsPerBooker = numberOfTickets;
        this.ticketsLeft = ticketsLeft;
    }

    public void bookTickets(String ticketType, int numberOfTickets, Booker booker, NumberOfTickets requestedNumberOfTickets) {
        if (exceedsNumberOfTicketsPerBooker(requestedNumberOfTickets)) {
            throw new NumberOfTicketsPerBuyerExceededException("Cannot reserve more tickets than allowed per buyer");
        }

        ticketsLeft.markTicketsAsReserved(numberOfTickets, ticketType, booker);
    }

    public boolean exceedsNumberOfTicketsPerBooker(NumberOfTickets requestedNumberOfTickets) {
        return numberOfTicketsPerBooker.value() < requestedNumberOfTickets.value();
    }

    public EventId getId() {
        return id;
    }

    public TicketsLeft getTicketsLeft() {
        return ticketsLeft;
    }

}

