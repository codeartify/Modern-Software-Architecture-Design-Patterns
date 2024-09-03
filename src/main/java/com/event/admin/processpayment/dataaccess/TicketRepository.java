package com.event.admin.processpayment.dataaccess;

import com.event.admin.domain.Ticket;
import com.event.admin.processpayment.domain.QrCode;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class TicketRepository {
    private final JdbcTemplate jdbcTemplate;

    public Ticket updateQrCodeFor(Ticket ticket, QrCode qrCode) {
        var value = qrCode.value();
        String query = "UPDATE ticket SET qr_code = ? WHERE id = ?";
        jdbcTemplate.update(query, value, ticket.getId());
        ticket.setQrCode(value);
        return ticket;
    }
}
