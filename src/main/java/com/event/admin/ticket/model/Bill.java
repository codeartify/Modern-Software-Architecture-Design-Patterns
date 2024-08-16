package com.event.admin.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
