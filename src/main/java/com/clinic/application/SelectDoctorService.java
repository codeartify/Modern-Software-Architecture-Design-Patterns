package com.clinic.application;

import com.clinic.domain.Doctor;
import com.clinic.domain.Patient;
import com.clinic.domain.Urgency;
import com.clinic.application.port.in.ForSelectingDoctor;
import com.clinic.application.port.out.ForFindingDoctorByPreferences;
import com.clinic.application.port.out.ForFindingFirstAvailableDoctor;
import org.springframework.stereotype.Service;

@Service
public class SelectDoctorService implements ForSelectingDoctor {
    private final ForFindingDoctorByPreferences forFindingDoctorByPreferences;
    private final ForFindingFirstAvailableDoctor forFindingFirstAvailableDoctor;

    public SelectDoctorService(
            ForFindingFirstAvailableDoctor forFindingFirstAvailableDoctor,
            ForFindingDoctorByPreferences forFindingDoctorByPreferences) {
        this.forFindingFirstAvailableDoctor = forFindingFirstAvailableDoctor;
        this.forFindingDoctorByPreferences = forFindingDoctorByPreferences;
    }

    @Override
    public Doctor selectDoctorForPatient(Patient patient) {
        if (patient.getDoctorPreference() == null || patient.getUrgency() == null) {
            throw new IllegalArgumentException("Patient preferences or urgency are missing.");
        }

        Doctor doctor = findDoctorByPreferences(patient);
        if (doctor == null) {
            throw new RuntimeException("No suitable doctor found based on preferences.");
        }
        return doctor;
    }

    private Doctor findDoctorByPreferences(Patient patient) {
        if (patient.getUrgency() == Urgency.HIGH) {
            return forFindingFirstAvailableDoctor.findFirstAvailableDoctor();
        }

        return forFindingDoctorByPreferences.findDoctorByPreferences(patient.getDoctorPreference());
    }
}
