package com.event.admin.ticket.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public final class Ticket2 {
    private Long bookerId;
    private String type;


    public void bookedBy(Long bookerId) {
        this.bookerId = bookerId;
    }

    boolean isReserved() {
        return getBookerId() != null && getBookerId() > 0;
    }

    boolean canBeReserved() {
        return !isReserved();
    }

    boolean isOfType(String ticketType) {
        return ticketType.equals(getType());
    }
}
