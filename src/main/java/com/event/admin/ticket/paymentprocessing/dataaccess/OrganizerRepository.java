package com.event.admin.ticket.paymentprocessing.dataaccess;

import com.event.admin.ticket.model.Organizer;
import com.event.admin.ticket.paymentprocessing.domain.OrganizerCompanyName;
import lombok.AllArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@Repository
public class OrganizerRepository {
    private final JdbcTemplate jdbcTemplate;

    public Organizer findByOrganizerCompanyName(OrganizerCompanyName organizerCompanyName) {
        String sql = "SELECT * FROM organizer WHERE company_name = ?";

        List<Organizer> organizers = this.jdbcTemplate.query(
                sql,
                new Object[]{organizerCompanyName.value()},
                (rs, _) -> toOrganizer(rs)
        );

        return DataAccessUtils.singleResult(organizers);
    }

    private static Organizer toOrganizer(ResultSet rs) throws SQLException {
        Organizer organizer = new Organizer();
        organizer.setId(rs.getLong("id"));
        organizer.setCompanyName(rs.getString("company_name"));
        organizer.setContactName(rs.getString("contact_name"));
        return organizer;
    }
}
