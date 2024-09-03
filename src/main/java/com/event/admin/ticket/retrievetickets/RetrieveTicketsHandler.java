package com.event.admin.ticket.retrievetickets;

import com.event.admin.ticket.domain.Event;
import com.event.admin.ticket.domain.Ticket;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RetrieveTicketsHandler {
    private JdbcTemplate jdbcTemplate;

    List<Ticket> handle() {
        log.info("Fetching all tickets...");

        String query = "SELECT id, price, type, qr_code, booker_id, event_id FROM ticket";
        List<Ticket> tickets = jdbcTemplate.query(query, (rs, rowNum) -> {
            Ticket ticket = new Ticket();
            ticket.setId(rs.getLong("id"));
            ticket.setPrice(rs.getDouble("price"));
            ticket.setType(rs.getString("type"));
            ticket.setQrCode(rs.getString("qr_code"));
            ticket.setBookerId(rs.getLong("booker_id"));

            Event event = new Event();
            event.setId(rs.getLong("event_id"));
            ticket.setEvent(event);

            return ticket;
        });

        log.info("Tickets fetched: {}", tickets.size());
        return tickets;
    }
}
