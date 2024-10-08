package com.event.admin.ticket.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Notification {
    private Long id;
    private String recipient;
    private String subject;
    private String message;
}
