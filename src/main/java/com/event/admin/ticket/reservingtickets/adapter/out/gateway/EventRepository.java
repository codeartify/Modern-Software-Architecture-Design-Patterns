package com.event.admin.ticket.reservingtickets.adapter.out.gateway;

import com.event.admin.ticket.reservingtickets.application.usecase.ports.out.gateway.FindEvent;
import com.event.admin.ticket.reservingtickets.application.usecase.ports.out.gateway.UpdateEvent;
import com.event.admin.ticket.reservingtickets.domain.*;
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
    public Optional<SelectedEvent> findById(EventId eventId) {
        var query = "SELECT * FROM event e INNER JOIN ticket t on e.id = t.event_id WHERE e.id = ?";
        return jdbcTemplate.query(query, new Object[]{eventId.value()}, (rs, _) -> toEvent(rs)).stream().findFirst();
    }

    private static SelectedEvent toEvent(ResultSet rs) throws SQLException {
        var tickets = new ArrayList<ReservableTicket>();
        Long eventId = null;
        Integer ticketsPerBooker = null;
        do {
            tickets.add(new ReservableTicket(new BookerId(rs.getLong("booker_id")), TicketType.getValueOf(rs.getString("type"))));
            if (eventId == null) {
                eventId = rs.getLong("id");
                ticketsPerBooker = rs.getInt("tickets_per_booker");
            }
        } while (rs.next());
        var ticketsLeft = new TicketsLeft(tickets);
        return new SelectedEvent(new EventId(eventId), ticketsLeft, new NumberOfTickets(ticketsPerBooker));
    }

    @Override
    public SelectedEvent withValue(SelectedEvent event) {
        event.getTicketsLeft().ticketsLeft()
                .stream()
                .filter(ReservableTicket::isReserved)
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
                    ticket.getBookerId().value(),
                            event.getId().value(),
                            ticket.getTicketType().name());
        });
        return event;
    }
}
