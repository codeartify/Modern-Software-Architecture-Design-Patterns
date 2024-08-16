package com.event.admin.ticket.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DiscountCode {
    private Long id;
    private String code;
    private double discountPercentage;
    private String applicableTicketType;
}
