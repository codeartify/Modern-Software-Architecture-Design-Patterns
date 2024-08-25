package com.event.admin.ticket.reservingTickets.domain;

import com.event.admin.ticket.reservingTickets.domain.exception.NumberOfTicketsPerBuyerExceededException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@DDDAggregate
@Getter
@AllArgsConstructor
@ToString
public class SelectedEvent {
    private Long id;
    private TicketsLeft ticketsLeft;
    private final int numberOfTicketsPerBooker;

    public void bookTickets(String ticketType, int numberOfTickets, Booker booker) {
        if (exceedsNumberOfTicketsPerBooker(numberOfTickets)) {
            throw new NumberOfTicketsPerBuyerExceededException("Cannot reserve more tickets than allowed per buyer");
        }

        ticketsLeft.markTicketsAsReserved(numberOfTickets, ticketType, booker);
    }

    public boolean exceedsNumberOfTicketsPerBooker(int numberOfTickets) {
        return numberOfTickets > this.getNumberOfTicketsPerBooker();
    }
}

