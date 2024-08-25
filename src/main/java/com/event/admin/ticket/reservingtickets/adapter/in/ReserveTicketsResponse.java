package com.event.admin.ticket.reservingtickets.adapter.in;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ReserveTicketsResponse {
    private long eventId;
    private int numberOfReservedTickets;
    private String bookerUsername;
}
