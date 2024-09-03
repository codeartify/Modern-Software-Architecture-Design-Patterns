package com.event.admin.ticket.createtickets;

import com.event.admin.ticket.domain.*;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
 public class TicketController {

    private final JdbcTemplate jdbcTemplate;

    public TicketController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/tickets")
    @Transactional
    public ResponseEntity<List<Ticket>> createTickets(@Valid @RequestBody List<Ticket> tickets) {
        log.info("Creating tickets...");
        log.info("Number of tickets: {}", tickets.size());
        log.info("Tickets: {}", tickets);
        if (tickets.size() > 10) {
            throw new IllegalArgumentException("Cannot purchase more than 10 tickets at a time.");
        }

        String query = "INSERT INTO ticket (price, type, qr_code, booker_id, event_id) VALUES(?, ?, ?, ?, ?)";
        for (Ticket ticket : tickets) {
            log.info("Ticket {}", ticket);
            jdbcTemplate.update(query, ticket.getPrice(), ticket.getType(), ticket.getQrCode(), ticket.getBookerId(), ticket.getEvent().getId());
        }
        log.info("Tickets created: {}", tickets.size());
        tickets.forEach(ticket -> log.info("Ticket type: {}, price: {}", ticket.getType(), ticket.getPrice()));
        return ResponseEntity.ok(tickets);
    }


}
