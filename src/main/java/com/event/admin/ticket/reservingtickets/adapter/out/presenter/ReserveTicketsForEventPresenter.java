package com.event.admin.ticket.reservingtickets.adapter.out.presenter;

import com.event.admin.ticket.reservingtickets.adapter.in.ReserveTicketsResponse;
import com.event.admin.ticket.reservingtickets.application.usecase.ports.out.presenter.PresentBookTicketsFailure;
import com.event.admin.ticket.reservingtickets.application.usecase.ports.out.presenter.PresentBookTicketsSuccess;
import com.event.admin.ticket.reservingtickets.domain.BookerUsername;
import com.event.admin.ticket.reservingtickets.domain.EventId;
import com.event.admin.ticket.reservingtickets.domain.NumberOfTickets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Slf4j
public class ReserveTicketsForEventPresenter
        implements
        PresentBookTicketsSuccess,
        PresentBookTicketsFailure {

    private ReserveTicketsResponse body;
    private Exception error;

    @Override
    public void present(EventId eventId, NumberOfTickets numberOfTickets, BookerUsername bookerUsername) {
        this.body = new ReserveTicketsResponse(
                eventId.value(),
                numberOfTickets.value(),
                bookerUsername.value());
    }

    @Override
    public void present(Exception e) {
        this.error = e;
    }

    public ResponseEntity<?> getError() {
        var errorResponse = switch (error.getClass().getSimpleName()) {
            case "MissingBookerException", "MissingEventException" -> ResponseEntity.notFound().build();
            case "AllTicketsSoldException", "TooFewTicketsOfTypeLeftException" -> ResponseEntity.noContent().build();
            case "NumberOfTicketsPerBuyerExceededException" -> ResponseEntity.badRequest().body(error.getMessage());
            default -> ResponseEntity.internalServerError().body(error.getMessage());
        };

        log.error("Error: {}", error.getMessage());
        return errorResponse;
    }

    public ResponseEntity<ReserveTicketsResponse> getSuccess() {
        return ResponseEntity.ok(body);
    }

    public boolean hasError() {
        return error != null;
    }
}
