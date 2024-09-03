package com.event.admin.ticket.processpayment.application.shared;

import com.event.admin.ticket.domain.Bill;
import com.event.admin.ticket.domain.Notification;
import com.event.admin.ticket.domain.Organizer;
import com.event.admin.ticket.domain.Ticket;
import com.event.admin.ticket.processpayment.dataaccess.Notifications;
import com.event.admin.ticket.processpayment.dataaccess.OrganizerRepository;
import com.event.admin.ticket.processpayment.domain.BuyerName;
import com.event.admin.ticket.processpayment.domain.OrganizerCompanyName;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {
    private final OrganizerRepository organizerRepository;
    private final Notifications notifications;

    public void notifyBuyer(Bill bill) {
        var buyerNotification = Notification.builder()
                .recipient(bill.getBuyerName())
                .subject("New Bill Issued")
                .message("""
                        A new bill has been issued to your company. Please check your details:
                        Amount: %s
                        Description: %s""".formatted(bill.getAmount(), bill.getDescription()))
                .build();

        notifications.sendNotification(buyerNotification);
    }

    public void notifyOrganizer(OrganizerCompanyName organizerCompanyName) {
        Organizer organizer = this.organizerRepository.findByOrganizerCompanyName(organizerCompanyName);

        if (organizer != null) {
            var organizerNotification = Notification.builder()
                    .recipient(organizer.getContactName())
                    .subject("New Ticket Sale")
                    .message("A new ticket sale has been processed. Please check your event dashboard.")
                    .build();

            notifications.sendNotification(organizerNotification);
        }
    }

    public void notifyPerTicket(Ticket ticket, BuyerName buyerName) {
        var notification = Notification.builder()
                .recipient(buyerName.value())
                .subject("Payment Successful")
                .message("""
                        Your payment was successful. Here is your ticket:
                        Event: %s
                        Ticket Type: %s
                        QR Code: %s""".formatted(ticket.getEvent().getName(), ticket.getType(), ticket.getQrCode()))
                .build();

        notifications.sendNotification(notification);
    }
}
