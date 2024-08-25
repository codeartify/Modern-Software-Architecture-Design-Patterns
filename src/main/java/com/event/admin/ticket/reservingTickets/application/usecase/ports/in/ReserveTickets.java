package com.event.admin.ticket.reservingTickets.application.usecase.ports.in;

import com.event.admin.ticket.reservingTickets.application.usecase.ReserveTicketsInput;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.presenter.PresentBookTicketsFailure;
import com.event.admin.ticket.reservingTickets.application.usecase.ports.out.presenter.PresentBookTicketsSuccess;

public interface ReserveTickets {
    void execute(
            ReserveTicketsInput reserveTicketsInput,
            PresentBookTicketsSuccess presentSuccess,
            PresentBookTicketsFailure presentFailure);

}
