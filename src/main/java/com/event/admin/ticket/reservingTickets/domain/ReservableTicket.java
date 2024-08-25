package com.event.admin.ticket.reservingTickets.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public final class ReservableTicket {
    private Long bookerId;
    private String type;

    public ReservableTicket(Long bookerId, String type) {
        this.bookerId = bookerId;
        this.type = type;
    }

    public void bookedBy(Long bookerId) {
        this.bookerId = bookerId;
    }

    public boolean isReserved() {
        return getBookerId() != null && getBookerId() > 0;
    }

    public boolean canBeReserved() {
        return !isReserved();
    }

    public boolean isOfType(String ticketType) {
        return ticketType.equals(getType());
    }
}
