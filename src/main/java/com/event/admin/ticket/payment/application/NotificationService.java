package com.event.admin.ticket.payment.application;

import com.event.admin.ticket.model.Bill;
import com.event.admin.ticket.model.Notification;
import com.event.admin.ticket.model.Organizer;
import com.event.admin.ticket.payment.dataaccess.OrganizerRepository;
import com.event.admin.ticket.payment.domain.OrganizerCompanyName;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {
    private final JdbcTemplate jdbcTemplate;
    private final OrganizerRepository organizerRepository;

    public void notifyBuyer(Bill bill) {
        var buyerNotification = Notification.builder()
                .recipient(bill.getBuyerName())
                .subject("New Bill Issued")
                .message("A new bill has been issued to your company. Please check your details:\n" + "Amount: " + bill.getAmount() + "\n" + "Description: " + bill.getDescription())
                .build();

        sendNotification(buyerNotification);
    }

    public void notifyOrganizer(OrganizerCompanyName organizerCompanyName) {
        Organizer organizer = this.organizerRepository.findByOrganizerCompanyName(organizerCompanyName);

        if (organizer != null) {
            var organizerNotification = Notification.builder()
                    .recipient(organizer.getContactName())
                    .subject("New Ticket Sale")
                    .message("A new ticket sale has been processed. Please check your event dashboard.")
                    .build();

            sendNotification(organizerNotification);
        }
    }

    private void sendNotification(Notification notification) {
        this.jdbcTemplate.update("INSERT INTO notification (recipient, subject, message) VALUES (?, ?, ?)", notification.getRecipient(), notification.getSubject(), notification.getMessage());
    }
}
