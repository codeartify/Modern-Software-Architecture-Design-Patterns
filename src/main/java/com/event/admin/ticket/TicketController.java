package com.event.admin.ticket;

import com.event.admin.ticket.mock.SecurityUtil;
import com.event.admin.ticket.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final JdbcTemplate jdbcTemplate;

    public TicketController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Endpoint to create a new event
    @PostMapping("/event")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        try {
            String query = "INSERT INTO events (name) VALUES (?)";
            jdbcTemplate.update(query, event.getName());
            return ResponseEntity.ok(event);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Endpoint to create tickets for an event
    @PostMapping("/create")
    public ResponseEntity<List<Ticket>> createTickets(@RequestBody List<Ticket> tickets) {
        try {
            if (tickets.size() > 10) {
                throw new IllegalArgumentException("Cannot purchase more than 10 tickets at a time.");
            }

            String query = "INSERT INTO tickets (price, type, event_id) VALUES (?, ?, ?)";
            for (Ticket ticket : tickets) {
                if (ticket.getPrice() < 0) {
                    throw new IllegalArgumentException("Ticket price cannot be negative.");
                }
                jdbcTemplate.update(query, ticket.getPrice(), ticket.getType(), ticket.getEvent().getId());
            }

            return ResponseEntity.ok(tickets);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Endpoint to process a payment
    @PostMapping("/payment")
    public ResponseEntity<Payment> processPayment(@RequestParam String paymentType,
                                                  @RequestBody List<Ticket> tickets,
                                                  @RequestParam String paymentMethod,
                                                  @RequestParam(required = false) String discountCode,
                                                  @RequestParam(required = false) String buyerCompanyName,
                                                  @RequestParam(required = false) String buyerName,
                                                  @RequestParam(required = false) String iban,
                                                  @RequestParam(required = false) String billDescription) {
        try {
            double totalAmount = 0;
            double discountTotal = 0;

            if (tickets.size() > 10) {
                throw new IllegalArgumentException("Cannot purchase more than 10 tickets at a time.");
            }

            for (Ticket ticket : tickets) {
                double ticketPriceWithVAT = ticket.getPrice() * 1.20;
                totalAmount += ticketPriceWithVAT;

                if (discountCode != null && !discountCode.isEmpty()) {
                    String query = "SELECT * FROM discount_codes WHERE code = ?";
                    DiscountCode discount = jdbcTemplate.queryForObject(query, new Object[]{discountCode}, (rs, _) -> {
                        DiscountCode discount1 = new DiscountCode();
                        discount1.setId(rs.getLong("id"));
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

            int numberOfTickets = tickets.size();
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
            payment.setPaymentMethod(paymentMethod);

            if ("credit_card".equalsIgnoreCase(paymentType)) {
                SecurityUtil.encryptPaymentInfo(payment.getPaymentMethod());
                payment.setDescription("Payment for tickets via credit card");
                payment.setSuccessful(true);

                for (Ticket ticket : tickets) {
                    String qrCodeUrl = "http://example.com/qr?ticket=" + UUID.randomUUID().toString();
                    ticket.setQRCode(qrCodeUrl);

                    String query = "UPDATE tickets SET qr_code = ? WHERE id = ?";
                    jdbcTemplate.update(query, qrCodeUrl, ticket.getId());

                    // Send notification to the buyer
                    Notification buyerNotification = new Notification();
                    buyerNotification.setRecipient(buyerName);
                    buyerNotification.setSubject("Payment Successful");
                    buyerNotification.setMessage("Your payment was successful. Here is your ticket:\n" +
                            "Event: " + ticket.getEvent().getName() + "\n" +
                            "Ticket Type: " + ticket.getType() + "\n" +
                            "QR Code: " + qrCodeUrl);
                    String query1 = "INSERT INTO notifications (recipient, subject, message) VALUES (?, ?, ?)";
                    jdbcTemplate.update(query1, buyerNotification.getRecipient(), buyerNotification.getSubject(), buyerNotification.getMessage());
                }

                String query = "SELECT * FROM organizers WHERE company_name = ?";
                Organizer organizer = jdbcTemplate.queryForObject(query, new Object[]{"Codertify GmbH"}, (rs, _) -> {
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
                    String query1 = "INSERT INTO notifications (recipient, subject, message) VALUES (?, ?, ?)";
                    jdbcTemplate.update(query1, organizerNotification.getRecipient(), organizerNotification.getSubject(), organizerNotification.getMessage());
                }
            } else if ("bill".equalsIgnoreCase(paymentType)) {
                if (buyerCompanyName == null || buyerCompanyName.isEmpty()) {
                    throw new IllegalArgumentException("Buyer company name must be provided.");
                }
                if (buyerName == null || buyerName.isEmpty()) {
                    throw new IllegalArgumentException("Buyer name must be provided.");
                }
                if (iban == null || iban.isEmpty()) {
                    throw new IllegalArgumentException("IBAN must be provided.");
                }

                double totalAmountWithVAT = totalAmount * 1.20;
                double totalAmountWithFee = totalAmountWithVAT * 1.03;

                Bill bill = new Bill();
                bill.setBuyerCompanyName(buyerCompanyName);
                bill.setBuyerName(buyerName);
                bill.setAmount(totalAmountWithFee);
                bill.setIban(iban);
                bill.setDescription(billDescription);
                bill.setOrganizerCompanyName("Codertify GmbH");
                bill.setCreationDate(LocalDate.now());
                bill.setPaid(false);

                // Send notification to the buyer
                Notification buyerNotification = new Notification();
                buyerNotification.setRecipient(buyerName);
                buyerNotification.setSubject("New Bill Issued");
                buyerNotification.setMessage("A new bill has been issued to your company. Please check your details:\n" +
                        "Amount: " + bill.getAmount() + "\n" +
                        "Description: " + bill.getDescription());
                String query = "INSERT INTO notifications (recipient, subject, message) VALUES (?, ?, ?)";
                jdbcTemplate.update(query, buyerNotification.getRecipient(), buyerNotification.getSubject(), buyerNotification.getMessage());

                // Notify the organizer
                String query2 = "SELECT * FROM organizers WHERE company_name = ?";
                Organizer organizer = jdbcTemplate.queryForObject(query2, new Object[]{"Codertify GmbH"}, new RowMapper<Organizer>() {
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
                    String query1 = "INSERT INTO notifications (recipient, subject, message) VALUES (?, ?, ?)";
                    jdbcTemplate.update(query1, organizerNotification.getRecipient(), organizerNotification.getSubject(), organizerNotification.getMessage());
                }

                payment.setDescription("Bill payment for tickets");
                payment.setSuccessful(true);
            } else {
                throw new IllegalArgumentException("Invalid payment type.");
            }

            String query = "INSERT INTO payments (amount, payment_method, description, successful) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(query, payment.getAmount(), payment.getPaymentMethod(), payment.getDescription(), payment.isSuccessful());

            return ResponseEntity.ok(payment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
