package com.clinic.domain;

import java.time.LocalDate;

public class Patient {
    private final String id;
    private final String name;
    private final LocalDate birthDate;
    private final String privacyAgreement;
    private final Urgency urgency;
    private final DoctorPreference doctorPreference;

    public Patient(String id, String name, LocalDate birthDate, String privacyAgreement, Urgency urgency, DoctorPreference doctorPreference) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.privacyAgreement = privacyAgreement;
        this.urgency = urgency;
        this.doctorPreference = doctorPreference;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPrivacyAgreement() {
        return privacyAgreement;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public DoctorPreference getDoctorPreference() {
        return doctorPreference;
    }
}
