package com.event.admin.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    private Long id;
    private double price;
    private String type;
    private String qrCode;
    private Event event;
}
