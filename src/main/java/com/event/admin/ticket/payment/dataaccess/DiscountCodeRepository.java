package com.event.admin.ticket.payment.dataaccess;

import com.event.admin.ticket.model.Discount;
import com.event.admin.ticket.payment.domain.DiscountCode;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@Repository
public class DiscountCodeRepository {
    private final JdbcTemplate jdbcTemplate;

    public Discount fetchDiscountCode(DiscountCode discountCode) {
        if (discountCode.value() != null && !discountCode.value().isEmpty()) {
            String query = "SELECT * FROM discount_code WHERE code = ?";
            return jdbcTemplate.queryForObject(query, new Object[]{discountCode.value()}, (rs, _) -> toDiscountCode(rs));
        }
        return null;
    }

    private static Discount toDiscountCode(ResultSet rs) throws SQLException {
        Discount discount = new Discount();
        discount.setCode(rs.getString("code"));
        discount.setDiscountPercentage(rs.getDouble("discount_percentage"));
        discount.setApplicableTicketType(rs.getString("applicable_ticket_type"));
        return discount;
    }
}
