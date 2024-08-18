package com.event.admin.ticket.reservation.domain;

import java.util.List;

public record TicketsLeft(List<Ticket2> ticketsLeft) {

    public int count() {
        return ticketsLeft.size();
    }

    public long numberOfTicketsLeftForType(String ticketType) {
        return ticketsLeft()
                .stream()
                .filter(ticket -> ticketType.equals(ticket.getType()) && ticket.getBookerId() == 0)
                .count();
    }

    public void markTicketsAsReserved(int numberOfTickets, String ticketType, Booker booker) {
        for (Ticket2 ticket : ticketsLeft) {
            if (ticketType.equals(ticket.getType())) {
                ticket.bookedBy(booker.id());
                numberOfTickets--;
            }
            if (numberOfTickets == 0) {
                break;
            }
        }
    }
}
