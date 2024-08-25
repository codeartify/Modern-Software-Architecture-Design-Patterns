package com.event.admin.ticket.payment;

import com.event.admin.ticket.model.Organizer;
import com.event.admin.ticket.payment.domain.OrganizerCompanyName;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@Repository
public class OrganizerRepository {
    private final JdbcTemplate jdbcTemplate;

    Organizer findByOrganizerCompanyName(OrganizerCompanyName organizerCompanyName) {
        return this.jdbcTemplate.queryForObject(
                "SELECT * FROM organizer WHERE company_name = ?", new Object[]{organizerCompanyName.value()}, new RowMapper<>() {
                    @Override
                    public Organizer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return toOrganizer(rs);
                    }

                    private static Organizer toOrganizer(ResultSet rs) throws SQLException {
                        Organizer organizer = new Organizer();
                        organizer.setId(rs.getLong("id"));
                        organizer.setCompanyName(rs.getString("company_name"));
                        organizer.setContactName(rs.getString("contact_name"));
                        return organizer;
                    }
                });
    }
}
