package com.event.admin.ticket.createevents;

import com.event.admin.ticket.domain.Event;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api")
 public class EventsController {

    private final JdbcTemplate jdbcTemplate;

    public EventsController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // Endpoint to create a new event
    @PostMapping("/events")
    @Transactional
    public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO event (name, tickets_per_booker) VALUES (?, ?)", new String[]{"id"});
            ps.setString(1, event.getName());
            ps.setInt(2, event.getNumberOfTicketsPerBooker());
            return ps;
        }, keyHolder);

        long newEventId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        Event createdEvent = jdbcTemplate.queryForObject(
                "SELECT id, name, tickets_per_booker FROM event WHERE id = ?",
                new Object[]{newEventId},
                (rs, rowNum) -> new Event(rs.getLong("id"), rs.getString("name"), rs.getInt("tickets_per_booker")
                )
        );

        return ResponseEntity.ok(createdEvent);
    }
}
