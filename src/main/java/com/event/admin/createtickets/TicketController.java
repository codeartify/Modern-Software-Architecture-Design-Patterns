package com.event.admin.createtickets;

import com.event.admin.domain.Ticket;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class TicketController {

    private TicketHandler ticketHandler;

    @PostMapping("/tickets")
    @Transactional
    public ResponseEntity<List<Ticket>> createTickets(@Valid @RequestBody List<Ticket> tickets) {
        var updatedTickets = ticketHandler.handle(tickets);
        return ResponseEntity.ok(updatedTickets);
    }


}
