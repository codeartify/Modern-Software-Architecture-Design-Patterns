package com.event.admin.ticket.model;

import jakarta.validation.Valid;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PaymentRequest {
    private String paymentType;
    private List<@Valid Ticket> tickets;
    private String paymentMethod;
    private String discountCode;
    private String buyerCompanyName;
    private String buyerName;
    private String iban;
    private String billDescription;
}
