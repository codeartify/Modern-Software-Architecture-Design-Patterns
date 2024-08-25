package com.event.admin.ticket.reservingtickets.domain;

import com.event.admin.ticket.reservingtickets.domain.exception.AllTicketsSoldException;
import com.event.admin.ticket.reservingtickets.domain.exception.TooFewTicketsOfTypeLeftException;

import java.util.List;

public record TicketsLeft(List<ReservableTicket> ticketsLeft) {

    public void markTicketsAsReserved(Booker booker, TicketType ticketType, NumberOfTickets numberOfRequestedTickets) {
        if (noTicketsLeft()) {
            throw new AllTicketsSoldException("No tickets left for the event");
        }

        if (notEnoughTicketsOfTypeLeft(numberOfRequestedTickets, ticketType)) {
            throw new TooFewTicketsOfTypeLeftException("Not enough tickets of type " + ticketType.name() + " left for the event");
        }

        for (ReservableTicket ticket : ticketsLeft) {
            if (ticket.isOfType(ticketType)) {
                ticket.bookedBy(booker.id());
                numberOfRequestedTickets.decrement();
            }
            if (numberOfRequestedTickets.isZero()) {
                break;
            }
        }
    }

    private boolean notEnoughTicketsOfTypeLeft(NumberOfTickets requestedNumberOfTickets, TicketType ticketType) {
        return requestedNumberOfTickets.value() > numberOfTicketsLeftForType(ticketType);
    }

    private long numberOfTicketsLeftForType(TicketType ticketType) {
        return ticketsLeft()
                .stream()
                .filter(ticket -> ticket.isRequestedTicket(ticketType))
                .count();
    }

    private boolean noTicketsLeft() {
        return !hasReservableTickets();
    }

    private boolean hasReservableTickets() {
        return ticketsLeft.stream()
                .anyMatch(ReservableTicket::canBeReserved);
    }

}
