package com.event.admin.ticket.domain;

import lombok.*;

@Getter
@Setter
@Builder
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
