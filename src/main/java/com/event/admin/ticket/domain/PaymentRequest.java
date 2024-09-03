package com.event.admin.ticket.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PaymentRequest {
    private List<@Valid Ticket> tickets;
    private String paymentMethod;
    private String discountCode;
    private String buyerCompanyName;
    private String buyerName;
    private String iban;
    private String billDescription;
    @NotBlank(message = "Payment must go to a company")
    private String organizerCompanyName;
}
