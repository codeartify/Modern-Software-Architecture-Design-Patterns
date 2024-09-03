package com.event.admin.createevents;

import com.event.admin.domain.Event;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.Objects;

@AllArgsConstructor
@Service
@Slf4j
public class EventsHandler {
    private JdbcTemplate jdbcTemplate;

    Event handle(Event event) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO event (name, tickets_per_booker) VALUES (?, ?)", new String[]{"id"});
            ps.setString(1, event.getName());
            ps.setInt(2, event.getNumberOfTicketsPerBooker());
            return ps;
        }, keyHolder);

        long newEventId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return this.jdbcTemplate.queryForObject(
                "SELECT id, name, tickets_per_booker FROM event WHERE id = ?",
                new Object[]{newEventId},
                (rs, rowNum) -> new Event(rs.getLong("id"), rs.getString("name"), rs.getInt("tickets_per_booker")
                )
        );
    }
}
