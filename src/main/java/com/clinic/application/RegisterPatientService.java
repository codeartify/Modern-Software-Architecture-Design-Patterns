package com.clinic.application;

import com.clinic.domain.Patient;
import com.clinic.port.in.ForRegisteringPatient;
import com.clinic.port.out.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterPatientService implements ForRegisteringPatient {
    private final PatientRepository patientRepository;

    public RegisterPatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void registerPatient(Patient patient) {
        validatePatientDetails(patient);

        if (patient.getPrivacyAgreement() == null || !patient.getPrivacyAgreement().equals("AGREED")) {
            throw new IllegalArgumentException("Patient must agree to privacy terms.");
        }

        patientRepository.save(patient);
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
