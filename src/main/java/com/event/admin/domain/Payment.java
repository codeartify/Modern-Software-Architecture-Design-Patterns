package com.event.admin.domain;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Payment {
    private Long id;
    private double amount;
    private String paymentMethod;
    private String description;
    private boolean successful;
}
