package com.event.admin.ticket.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Discount {
    private Long id;
    private String code;
    private double discountPercentage;
    private String applicableTicketType;
}
