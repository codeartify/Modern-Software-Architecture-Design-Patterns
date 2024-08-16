package com.event.admin.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCode {
    private Long id;
    private String code;
    private double discountPercentage;
    private String applicableTicketType;
}
