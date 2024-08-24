package com.event.admin.ticket;

import com.event.admin.ticket.model.*;
import com.event.admin.ticket.payment.PaymentFactory;
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
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api")
 public class TicketController {

    private final JdbcTemplate jdbcTemplate;
    private final PaymentFactory paymentFactory;

    public TicketController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.paymentFactory = PaymentFactory.createPaymentFactory(jdbcTemplate);
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
    // Endpoint to create tickets for an event

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

    // Endpoint to process a payment
    @PostMapping("/tickets/payment")
    @Transactional
    public ResponseEntity<Payment> processPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        log.info("Processing payment...");
        log.info("Payment request: {}", paymentRequest);


        Payment payment = paymentFactory.createPayment(paymentRequest);

        updatePayment(payment);

        return ResponseEntity.ok(payment);

    }

    private void updatePayment(Payment payment) {
        String query = "INSERT INTO payment (amount, payment_method, description, successful) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(query, payment.getAmount(), payment.getPaymentMethod(), payment.getDescription(), payment.isSuccessful());
    }

}
