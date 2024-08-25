package com.event.admin.ticket.payment;

import com.event.admin.ticket.model.DiscountCode;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@Repository
public class DiscountCodeRepository {
    private final JdbcTemplate jdbcTemplate;

    DiscountCode fetchDiscountCode(String discountCode) {
        if (discountCode != null && !discountCode.isEmpty()) {
            String query = "SELECT * FROM discount_code WHERE code = ?";
            return jdbcTemplate.queryForObject(query, new Object[]{discountCode}, (rs, _) -> toDiscountCode(rs));
        }
        return null;
    }

    static DiscountCode toDiscountCode(ResultSet rs) throws SQLException {
        DiscountCode discount = new DiscountCode();
        discount.setCode(rs.getString("code"));
        discount.setDiscountPercentage(rs.getDouble("discount_percentage"));
        discount.setApplicableTicketType(rs.getString("applicable_ticket_type"));
        return discount;
    }
}
