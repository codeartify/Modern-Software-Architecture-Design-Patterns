package com.clinic.application;

import com.clinic.domain.Patient;
import com.clinic.port.in.ForRegisteringPatient;
import com.clinic.port.out.ForStoringPatients;
import org.springframework.stereotype.Service;

@Service
public class RegisterPatientUseCase implements ForRegisteringPatient {
    private final ForStoringPatients forStoringPatients;

    public RegisterPatientUseCase(ForStoringPatients forStoringPatients) {
        this.forStoringPatients = forStoringPatients;
    }

    @Override
    public void registerPatient(Patient patient) {
        validatePatientDetails(patient);

        if (patient.getPrivacyAgreement() == null || !patient.getPrivacyAgreement().equalsIgnoreCase("AGREED")) {
            throw new IllegalArgumentException("Patient must agree to privacy terms.");
        }

        forStoringPatients.store(patient);
    }

    private void validatePatientDetails(Patient patient) {
        if (patient.getName() == null || patient.getName().isEmpty()) {
            throw new IllegalArgumentException("Patient name cannot be empty.");
        }
        if (patient.getBirthDate() == null) {
            throw new IllegalArgumentException("Patient birth date must be provided.");
        }
    }
}
