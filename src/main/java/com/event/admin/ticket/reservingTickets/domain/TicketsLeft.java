package com.event.admin.ticket.reservingTickets.domain;

import com.event.admin.ticket.reservingTickets.domain.exception.AllTicketsSoldException;
import com.event.admin.ticket.reservingTickets.domain.exception.TooFewTicketsOfTypeLeftException;

import java.util.List;

public record TicketsLeft(List<ReservableTicket> ticketsLeft) {

    public void markTicketsAsReserved(int numberOfTickets, String ticketType, Booker booker) {
        if (noTicketsLeft()) {
            throw new AllTicketsSoldException("No tickets left for the event");
        }

        if (notEnoughTicketsOfTypeLeft(numberOfTickets, ticketType)) {
            throw new TooFewTicketsOfTypeLeftException("Not enough tickets of type " + ticketType + " left for the event");
        }
        for (ReservableTicket ticket : ticketsLeft) {
            if (ticket.isOfType(TicketType.getValueOf(ticketType))) {
                ticket.bookedBy(new BookerId(booker.id()));
            }
            if (ticket.isOfType(TicketType.getValueOf(ticketType))) {
                numberOfTickets--;
            }
            if (numberOfTickets == 0) {
                break;
            }
        }
    }

    private boolean notEnoughTicketsOfTypeLeft(int numberOfTickets, String ticketType) {
        return numberOfTickets > numberOfTicketsLeftForType(ticketType);
    }

    private long numberOfTicketsLeftForType(String ticketType) {
        return ticketsLeft()
                .stream()
                .filter(ticket -> ticket.isOfType(TicketType.getValueOf(ticketType)) && ticket.canBeReserved())
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
