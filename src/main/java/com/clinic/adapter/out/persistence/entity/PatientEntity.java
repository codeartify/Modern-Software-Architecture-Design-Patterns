package com.clinic.adapter.out.persistence.entity;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PatientEntity {
    private String id;
    private String name;
    private LocalDate birthDate;
    private String privacyAgreement;
    private String preferredDoctorId;
    private String urgency; // Use string to represent urgency
}
