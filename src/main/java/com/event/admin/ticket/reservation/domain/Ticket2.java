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

}
