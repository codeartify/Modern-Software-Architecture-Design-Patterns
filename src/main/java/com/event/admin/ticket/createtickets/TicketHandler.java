package com.event.admin.ticket.createtickets;

import com.event.admin.ticket.domain.Ticket;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class TicketHandler {
    private final JdbcTemplate jdbcTemplate;

    public List<Ticket> handle(List<Ticket> tickets) {
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
        return tickets;
    }
}
