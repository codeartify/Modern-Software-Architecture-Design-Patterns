package com.clinic.adapter.out.persistence.entity;

import com.clinic.domain.Urgency;

import java.time.LocalDate;

public class PatientEntity {
    private String id;
    private String name;
    private LocalDate birthDate;
    private String privacyAgreement;
    private String preferredDoctorId;
    private String urgency; // Use string to represent urgency

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setPrivacyAgreement(String privacyAgreement) {
        this.privacyAgreement = privacyAgreement;
    }

    public void setPreferredDoctorId(String preferredDoctorId) {
        this.preferredDoctorId = preferredDoctorId;
    }

    public void setUrgency(String name) {
        this.urgency = name;
    }

    public String getname() {
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

    public String getUrgency() {
        return urgency;
    }
}
