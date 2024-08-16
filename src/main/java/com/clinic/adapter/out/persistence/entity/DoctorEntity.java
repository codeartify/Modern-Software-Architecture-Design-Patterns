package com.clinic.adapter.out.persistence.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DoctorEntity {
    private String id;
    private String name;
    private String specialization;
    private String language;
    private String gender;
    private boolean isAvailable;
}
