package com.event.admin.ticket.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Bill {
    private Long id;
    private String buyerCompanyName;
    private String buyerName;
    private double amount;
    private String iban;
    private String description;
    private String organizerCompanyName;
    private LocalDate creationDate;
    private boolean paid;
}
