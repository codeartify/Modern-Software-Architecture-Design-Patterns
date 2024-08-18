package com.event.admin.ticket.reservation.adapter.out.presenter;

import com.event.admin.ticket.model.ReserveTicketsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Slf4j
public class ReserveTicketsForEventPresenter implements com.event.admin.ticket.reservation.application.ports.out.PresentBookTicketsSuccess, PresentBookTicketsFailure {
    private ReserveTicketsResponse body;
    private Exception error;

    @Override
    public void present(Long eventId, int numberOfTickets, String bookerUsername) {
        this.body = new ReserveTicketsResponse(
                eventId,
                numberOfTickets,
                bookerUsername);
    }

    @Override
    public void present(Exception e) {
        this.error = e;
    }

    public ResponseEntity getError() {
        var errorResponse = switch (error.getClass().getSimpleName()) {
            case "MissingBookerException" -> ResponseEntity.notFound().build();
            case "MissingEventException" -> ResponseEntity.notFound().build();
            case "AllTicketsSoldException" -> ResponseEntity.noContent().build();
            case "NumberOfTicketsPerBuyerExceededException" -> ResponseEntity.badRequest().body(error.getMessage());
            case "TooFewTicketsOfTypeLeftException" -> ResponseEntity.noContent().build();
            default -> ResponseEntity.internalServerError().body(error.getMessage());
        };

        log.error("Error: {}", error.getMessage());
        return errorResponse;
    }

    public ResponseEntity getSuccess() {
        return ResponseEntity.ok(body);
    }

    public boolean hasError() {
        return error != null;
    }
}
