package com.event.admin.ticket.reservingtickets.application.usecase.ports.out.presenter;

import com.event.admin.ticket.reservingtickets.application.usecase.ReserveTicketsOutput;

public interface PresentBookTicketsSuccess {
    void present(ReserveTicketsOutput reserveTicketsOutput);
}
