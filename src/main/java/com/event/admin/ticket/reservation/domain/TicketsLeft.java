package com.event.admin.ticket.reservation.domain;

import java.util.List;

public record TicketsLeft(List<Ticket2> ticketsLeft) {

    public long numberOfTicketsLeftForType(String ticketType) {
        return ticketsLeft()
                .stream()
                .filter(ticket -> ticket.isOfType(ticketType) && ticket.canBeReserved())
                .count();
    }

    public void markTicketsAsReserved(int numberOfTickets, String ticketType, Booker booker) {
        for (Ticket2 ticket : ticketsLeft) {
            if (ticket.isOfType(ticketType)) {
                ticket.bookedBy(booker.id());
            }
            if (ticket.isOfType(ticketType)) {
                numberOfTickets--;
            }
            if (numberOfTickets == 0) {
                break;
            }
        }
    }

    public boolean noTicketsLeft() {
        return !hasReservableTickets();
    }

    public boolean hasReservableTickets() {
        return ticketsLeft.stream()
                .anyMatch(Ticket2::canBeReserved);
    }

}
