package com.event.admin.ticket.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Notification {
    private Long id;
    private String recipient;
    private String subject;
    private String message;

    public static Notification createNotification(String recipient, String subject, String message) {
        Notification organizerNotification = new Notification();
        organizerNotification.setRecipient(recipient);
        organizerNotification.setSubject(subject);
        organizerNotification.setMessage(message);
        return organizerNotification;
    }
}
