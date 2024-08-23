package com.event.admin.ticket.reservation.adapter.out.gateway;

import com.event.admin.ticket.reservation.application.ports.out.gateway.FindBooker;
import com.event.admin.ticket.reservation.domain.Booker;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BookerRepository implements FindBooker {
    private final JdbcTemplate jdbcTemplate;

    public BookerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Booker> findByUsername(String bookerUsername) {
        var booker = jdbcTemplate.query("SELECT * FROM booker WHERE username = ?", new Object[]{bookerUsername}, (rs, rowNum) -> new Booker(rs.getLong("id")));
        return booker.stream().findFirst();
    }
}
