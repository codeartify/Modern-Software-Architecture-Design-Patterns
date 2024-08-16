package com.clinic.domain;

import java.net.ProtocolFamily;
import java.time.LocalDate;

public class Patient {
    private final String id;
    private final String name;
    private final LocalDate birthDate;
    private final String privacyAgreement;
    private final String preferredDoctorId;
    private final Urgency urgency;

    public Patient(String id, String name, LocalDate birthDate, String privacyAgreement, String preferredDoctorId, Urgency urgency) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.privacyAgreement = privacyAgreement;
        this.preferredDoctorId = preferredDoctorId;
        this.urgency = urgency;
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

    public String getPreferredDoctorId() {
        return preferredDoctorId;
    }

    public Urgency getUrgency() {
        return urgency;
    }

}
