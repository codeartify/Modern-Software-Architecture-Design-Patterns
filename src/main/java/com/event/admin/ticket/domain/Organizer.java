package com.event.admin.ticket.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Organizer {
    private Long id;
    private String companyName;
    private String contactName;
}
