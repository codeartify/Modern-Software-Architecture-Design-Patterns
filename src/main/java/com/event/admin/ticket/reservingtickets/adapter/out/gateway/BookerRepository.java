package com.event.admin.ticket.reservingtickets.adapter.out.gateway;

import com.event.admin.ticket.reservingtickets.application.usecase.ports.out.gateway.FindBooker;
import com.event.admin.ticket.reservingtickets.domain.Booker;
import com.event.admin.ticket.reservingtickets.domain.BookerId;
import com.event.admin.ticket.reservingtickets.domain.BookerUsername;
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
    public Optional<Booker> findByUsername(BookerUsername bookerUsername) {
        var booker = jdbcTemplate.query("SELECT * FROM booker WHERE username = ?", new Object[]{bookerUsername.value()}, (rs, _) -> new Booker(new BookerId(rs.getLong("id")), new BookerUsername(rs.getString("username"))));
        
        return booker.stream().findFirst();
    }
}
