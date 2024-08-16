package com.clinic.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
    private String id;
    private String name;
    private LocalDate birthDate;
    private String gender;
    private String privacyAgreement;
    private String preferredDoctorId;
    private String specialization;
    private String language;
    private Urgency urgency;

    public enum Urgency {
        LOW,
        MEDIUM,
        HIGH
    }
}
