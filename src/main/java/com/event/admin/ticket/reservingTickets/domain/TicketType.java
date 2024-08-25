package com.event.admin.ticket.reservingTickets.domain;

public enum TicketType {
    STANDARD, VIP;

    public static TicketType getValueOf(String type) {
        return valueOf(type.toUpperCase());
    }
}
