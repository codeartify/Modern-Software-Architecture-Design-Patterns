package com.event.admin.ticket.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Ticket {
    private Long id;
    private double price;
    private String type;
    private String qrCode;
    private Event event;
}
