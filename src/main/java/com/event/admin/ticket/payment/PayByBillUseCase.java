package com.event.admin.ticket.payment;

import com.event.admin.ticket.model.*;
import com.event.admin.ticket.payment.domain.BuyerName;
import com.event.admin.ticket.payment.domain.BuyerCompanyName;
import com.event.admin.ticket.payment.domain.Iban;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PayByBillUseCase implements PaymentUseCase {
    private final JdbcTemplate jdbcTemplate;
    private final TotalAmountFactory totalAmountFactory;

    public PayByBillUseCase(JdbcTemplate jdbcTemplate, TotalAmountFactory totalAmountFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.totalAmountFactory = totalAmountFactory;
    }

    @Override
    public Payment createPayment(PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        payment.setAmount(totalAmountFactory.calculateTotalAmount(paymentRequest));
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());

        var organizerCompanyName = paymentRequest.getOrganizerCompanyName();

        var buyerCompanyName = new BuyerCompanyName(paymentRequest.getBuyerCompanyName());
        var buyerName = new BuyerName(paymentRequest.getBuyerName());

        Bill bill = new Bill();
        bill.setBuyerCompanyName(buyerCompanyName.value());
        bill.setBuyerName(buyerName.value());
        bill.setAmount(totalAmountFactory.getTotalAmountWithFee(paymentRequest));
        bill.setIban(new Iban(paymentRequest.getIban()).value());
        bill.setDescription(paymentRequest.getBillDescription());
        bill.setOrganizerCompanyName(organizerCompanyName);
        bill.setCreationDate(LocalDate.now());
        bill.setPaid(false);

        // Send notification to the buyer
        Notification buyerNotification = new Notification();
        buyerNotification.setRecipient(buyerName.value());
        buyerNotification.setSubject("New Bill Issued");
        buyerNotification.setMessage("A new bill has been issued to your company. Please check your details:\n" + "Amount: " + bill.getAmount() + "\n" + "Description: " + bill.getDescription());
        String query = "INSERT INTO notification (recipient, subject, message) VALUES (?, ?, ?)";
        this.jdbcTemplate.update(query, buyerNotification.getRecipient(), buyerNotification.getSubject(), buyerNotification.getMessage());

        // Notify the organizer
        String query2 = "SELECT * FROM organizer WHERE company_name = ?";
        Organizer organizer = this.jdbcTemplate.queryForObject(query2, new Object[]{organizerCompanyName}, new RowMapper<Organizer>() {
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
            this.jdbcTemplate.update(query1, organizerNotification.getRecipient(), organizerNotification.getSubject(), organizerNotification.getMessage());
        }

        payment.setDescription("Bill payment for tickets");
        payment.setSuccessful(true);
        return payment;
    }

}
