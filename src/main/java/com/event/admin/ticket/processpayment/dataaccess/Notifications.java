package com.event.admin.ticket.processpayment.dataaccess;

import com.event.admin.ticket.domain.Notification;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class Notifications {
    private final JdbcTemplate jdbcTemplate;

    public void sendNotification(Notification notification) {
        var sql = "INSERT INTO notification (recipient, subject, message) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, notification.getRecipient(), notification.getSubject(), notification.getMessage());
    }
}
