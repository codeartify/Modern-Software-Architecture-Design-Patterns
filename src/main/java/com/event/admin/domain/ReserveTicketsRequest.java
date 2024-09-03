package com.event.admin.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ReserveTicketsRequest {
    @Min(value = 1, message = "Number of tickets requested must be larger 0")
    private int numberOfTickets;
    @NotBlank
    @Pattern(regexp = "Standard|VIP", message = "Ticket type must be either 'Standard' or 'VIP'")
    private String ticketType;
    @NotBlank
    private String bookerUsername;
}
