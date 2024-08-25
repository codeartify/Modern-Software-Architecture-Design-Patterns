package com.event.admin.ticket.reservingTickets.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Event2 {
    private Long id;
    private TicketsLeft ticketsLeft;
    private final int numberOfTicketsPerBooker;

    public boolean exceedsNumberOfTicketsPerBooker(int numberOfTickets) {
        return numberOfTickets > this.getNumberOfTicketsPerBooker();
    }

    public void updateTicketsLeft(TicketsLeft ticketsLeft) {
        this.ticketsLeft = ticketsLeft;
    }

}

