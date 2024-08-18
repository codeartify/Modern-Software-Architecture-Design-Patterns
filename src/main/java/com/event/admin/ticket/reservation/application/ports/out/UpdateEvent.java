package com.event.admin.ticket.reservation.application.ports.out;

import com.event.admin.ticket.reservation.domain.Event2;

public interface UpdateEvent {
    Event2 withValue(Event2 event);
}
