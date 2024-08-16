package com.event.admin.ticket.model;

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
    private List<Ticket> tickets;
    private String paymentMethod;
    private String discountCode;
    private String buyerCompanyName;
    private String buyerName;
    private String iban;
    private String billDescription;
}
