package com.event.admin.ticket.reservingtickets.domain;

public enum TicketType {
    STANDARD, VIP;

    public static TicketType getValueOf(String type) {
        return valueOf(type.toUpperCase());
    }
}
