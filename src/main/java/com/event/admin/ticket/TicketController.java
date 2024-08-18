package com.event.admin.ticket;

import com.event.admin.ticket.model.*;
import com.event.admin.ticket.reservation.adapter.out.presenter.ReserveTicketsForEventPresenter;
import com.event.admin.ticket.reservation.application.ports.in.ReserveTickets;
import com.event.admin.ticket.reservation.application.usecase.ReserveTicketsUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
public class TicketController {

    private final JdbcTemplate jdbcTemplate;
    private final ReserveTickets reserveTickets;

    public TicketController(JdbcTemplate jdbcTemplate, ReserveTickets reserveTickets) {
        this.jdbcTemplate = jdbcTemplate;
        this.reserveTickets = reserveTickets;

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


    @PostMapping("/events/{id}/tickets")
    @Transactional
    public ResponseEntity<Object> reserveTicketsForEvent(
            @PathVariable("id") @NotNull Long eventId,
            @Valid @RequestBody ReserveTicketsRequest reserveTicketsRequest) {
        log.info("Reserving tickets for event with ID: {}", eventId);
        log.info("Reserve tickets request: {}", reserveTicketsRequest);

        var presenter = new ReserveTicketsForEventPresenter();

        var ticketType = reserveTicketsRequest.getTicketType();
        var numberOfTickets = reserveTicketsRequest.getNumberOfTickets();
        var bookerUsername = reserveTicketsRequest.getBookerUsername();

        reserveTickets.execute(eventId, ticketType, numberOfTickets, bookerUsername, presenter, presenter);

        if (presenter.hasError()) {
            return presenter.getError();
        } else {
            return presenter.getSuccess();
        }
    }

    // Endpoint to process a payment
    @PostMapping("/tickets/payment")
    @Transactional
    public ResponseEntity<Payment> processPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        log.info("Processing payment...");
        log.info("Payment request: {}", paymentRequest);
        double totalAmount = 0;
        double discountTotal = 0;

        if (paymentRequest.getTickets().size() > 20) {
            throw new IllegalArgumentException("Cannot purchase more than 20 overall tickets at a time.");
        }

        for (Ticket ticket : paymentRequest.getTickets()) {
            double ticketPriceWithVAT = ticket.getPrice() * 1.20;
            totalAmount += ticketPriceWithVAT;

            if (paymentRequest.getDiscountCode() != null && !paymentRequest.getDiscountCode().isEmpty()) {
                String query = "SELECT * FROM discount_code WHERE code = ?";
                DiscountCode discount = jdbcTemplate.queryForObject(query, new Object[]{paymentRequest.getDiscountCode()}, (rs, _) -> {
                    DiscountCode discount1 = new DiscountCode();
                    discount1.setCode(rs.getString("code"));
                    discount1.setDiscountPercentage(rs.getDouble("discount_percentage"));
                    discount1.setApplicableTicketType(rs.getString("applicable_ticket_type"));
                    return discount1;
                });
                if (discount != null && (discount.getApplicableTicketType() == null || discount.getApplicableTicketType().equals(ticket.getType()))) {
                    discountTotal += ticketPriceWithVAT * discount.getDiscountPercentage() / 100;
                }
            }
        }

        int numberOfTickets = paymentRequest.getTickets().size();
        double groupDiscount;
        if (numberOfTickets >= 11) {
            groupDiscount = 0.30;
        } else if (numberOfTickets >= 6) {
            groupDiscount = 0.25;
        } else if (numberOfTickets >= 2) {
            groupDiscount = 0.20;
        } else {
            groupDiscount = 0;
        }
        discountTotal += totalAmount * groupDiscount;
        totalAmount -= discountTotal;

        double result;
        if (totalAmount > 40.00) {
            result = totalAmount * 0.025;
        } else {
            result = 0.99;
        }
        totalAmount += result;

        Payment payment = new Payment();
        payment.setAmount(totalAmount);
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());

        var organizerCompanyName = paymentRequest.getOrganizerCompanyName();
        if ("credit_card".equalsIgnoreCase(paymentRequest.getPaymentType())) {
            payment.setDescription("Payment for tickets via credit card");
            payment.setSuccessful(true);

            for (Ticket ticket : paymentRequest.getTickets()) {
                String qrCodeUrl = "http://example.com/qr?ticket=" + UUID.randomUUID();
                ticket.setQrCode(qrCodeUrl);

                String query = "UPDATE ticket SET qr_code = ? WHERE id = ?";
                jdbcTemplate.update(query, qrCodeUrl, ticket.getId());

                // Send notification to the buyer
                Notification buyerNotification = new Notification();
                buyerNotification.setRecipient(paymentRequest.getBuyerName());
                buyerNotification.setSubject("Payment Successful");
                buyerNotification.setMessage("Your payment was successful. Here is your ticket:\n" + "Event: " + ticket.getEvent().getName() + "\n" + "Ticket Type: " + ticket.getType() + "\n" + "QR Code: " + qrCodeUrl);
                String query1 = "INSERT INTO notification (recipient, subject, message) VALUES (?, ?, ?)";
                jdbcTemplate.update(query1, buyerNotification.getRecipient(), buyerNotification.getSubject(), buyerNotification.getMessage());
            }

            String query = "SELECT * FROM organizer WHERE company_name = ?";
            Organizer organizer = jdbcTemplate.queryForObject(query, new Object[]{organizerCompanyName}, (rs, _) -> {
                Organizer organizer1 = new Organizer();
                organizer1.setId(rs.getLong("id"));
                organizer1.setCompanyName(rs.getString("company_name"));
                organizer1.setContactName(rs.getString("contact_name"));
                return organizer1;
            });

            if (organizer != null) {
                Notification organizerNotification = new Notification();
                organizerNotification.setRecipient(organizer.getContactName());
                organizerNotification.setSubject("New Ticket Sale");
                organizerNotification.setMessage("A new ticket sale has been processed. Please check your event dashboard.");
                String query1 = "INSERT INTO notification (recipient, subject, message) VALUES (?, ?, ?)";
                jdbcTemplate.update(query1, organizerNotification.getRecipient(), organizerNotification.getSubject(), organizerNotification.getMessage());
            }
        } else if ("bill".equalsIgnoreCase(paymentRequest.getPaymentType())) {
            if (paymentRequest.getBuyerCompanyName() == null || paymentRequest.getBuyerCompanyName().isEmpty()) {
                throw new IllegalArgumentException("Buyer company name must be provided.");
            }
            if (paymentRequest.getBuyerName() == null || paymentRequest.getBuyerName().isEmpty()) {
                throw new IllegalArgumentException("Buyer name must be provided.");
            }
            if (paymentRequest.getIban() == null || paymentRequest.getIban().isEmpty()) {
                throw new IllegalArgumentException("IBAN must be provided.");
            }

            double totalAmountWithVAT = totalAmount * 1.20;
            double totalAmountWithFee = totalAmountWithVAT * 1.03;

            Bill bill = new Bill();
            bill.setBuyerCompanyName(paymentRequest.getBuyerCompanyName());
            bill.setBuyerName(paymentRequest.getBuyerName());
            bill.setAmount(totalAmountWithFee);
            bill.setIban(paymentRequest.getIban());
            bill.setDescription(paymentRequest.getBillDescription());
            bill.setOrganizerCompanyName(organizerCompanyName);
            bill.setCreationDate(LocalDate.now());
            bill.setPaid(false);

            // Send notification to the buyer
            Notification buyerNotification = new Notification();
            buyerNotification.setRecipient(paymentRequest.getBuyerName());
            buyerNotification.setSubject("New Bill Issued");
            buyerNotification.setMessage("A new bill has been issued to your company. Please check your details:\n" + "Amount: " + bill.getAmount() + "\n" + "Description: " + bill.getDescription());
            String query = "INSERT INTO notification (recipient, subject, message) VALUES (?, ?, ?)";
            jdbcTemplate.update(query, buyerNotification.getRecipient(), buyerNotification.getSubject(), buyerNotification.getMessage());

            // Notify the organizer
            String query2 = "SELECT * FROM organizer WHERE company_name = ?";
            Organizer organizer = jdbcTemplate.queryForObject(query2, new Object[]{organizerCompanyName}, new RowMapper<Organizer>() {
                @Override
                public Organizer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Organizer organizer = new Organizer();
                    organizer.setId(rs.getLong("id"));
                    organizer.setCompanyName(rs.getString("company_name"));
                    organizer.setContactName(rs.getString("contact_name"));
                    return organizer;
                }
            });

            if (organizer != null) {
                Notification organizerNotification = new Notification();
                organizerNotification.setRecipient(organizer.getContactName());
                organizerNotification.setSubject("New Ticket Sale");
                organizerNotification.setMessage("A new ticket sale has been processed. Please check your event dashboard.");
                String query1 = "INSERT INTO notification (recipient, subject, message) VALUES (?, ?, ?)";
                jdbcTemplate.update(query1, organizerNotification.getRecipient(), organizerNotification.getSubject(), organizerNotification.getMessage());
            }

            payment.setDescription("Bill payment for tickets");
            payment.setSuccessful(true);
        } else {
            throw new IllegalArgumentException("Invalid payment type.");
        }

        String query = "INSERT INTO payment (amount, payment_method, description, successful) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(query, payment.getAmount(), payment.getPaymentMethod(), payment.getDescription(), payment.isSuccessful());

        return ResponseEntity.ok(payment);

    }
}
