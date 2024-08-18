package com.event.admin.ticket.reservation.adapter.out.gateway;

import com.event.admin.ticket.reservation.application.ports.out.FindEvent;
import com.event.admin.ticket.reservation.application.ports.out.UpdateEvent;
import com.event.admin.ticket.reservation.domain.Event2;
import com.event.admin.ticket.reservation.domain.Ticket2;
import com.event.admin.ticket.reservation.domain.TicketsLeft;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Repository
public class EventRepository implements FindEvent, UpdateEvent {

    private final JdbcTemplate jdbcTemplate;

    public EventRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Event2> findById(@NotNull Long eventId) {
        var query = "SELECT * FROM event e INNER JOIN ticket t on e.id = t.event_id WHERE e.id = ?";
        return jdbcTemplate.query(query, new Object[]{eventId}, (rs, _) -> toEvent(rs)).stream().findFirst();

    }

    private static Event2 toEvent(ResultSet rs) throws SQLException {
        var tickets = new ArrayList<Ticket2>();
        Long eventId = null;
        Integer ticketsPerBooker = null;
        do {
            tickets.add(new Ticket2(rs.getLong("booker_id"), rs.getString("type")));
            if (eventId == null) {
                eventId = rs.getLong("id");
                ticketsPerBooker = rs.getInt("tickets_per_booker");
            }
        } while (rs.next());
        var ticketsLeft = new TicketsLeft(tickets);
        return new Event2(eventId, ticketsLeft, ticketsPerBooker);
    }

    @Override
    public Event2 withValue(Event2 event) {
        event.getTicketsLeft().ticketsLeft()
                .stream()
                .filter(Ticket2::isReserved)
                .forEach(ticket -> {
            jdbcTemplate.update("UPDATE ticket \n" +
                            "SET booker_id = ?\n" +
                            "WHERE id = (\n" +
                            "    SELECT id FROM (\n" +
                            "        SELECT id, ROW_NUMBER() OVER (ORDER BY id) AS rn \n" +
                            "        FROM ticket \n" +
                            "        WHERE event_id = ? AND type = ? AND booker_id IS NULL\n" +
                            "    ) AS t WHERE rn = 1\n" +
                            ");",
                    ticket.getBookerId(),  // Corrected: Booker ID should be set here
                    event.getId(),         // Assuming event has a getId method
                    ticket.getType());
        });
        return event;
    }
}
