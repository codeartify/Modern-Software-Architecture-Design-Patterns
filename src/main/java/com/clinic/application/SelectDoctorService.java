package com.clinic.application;

import com.clinic.domain.Doctor;
import com.clinic.domain.Patient;
import com.clinic.domain.Urgency;
import com.clinic.port.in.ForSelectingDoctor;
import com.clinic.port.out.DoctorRepository;
import org.springframework.stereotype.Service;

@Service
public class SelectDoctorService implements ForSelectingDoctor {
    private final DoctorRepository doctorRepository;

    public SelectDoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
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
            return doctorRepository.findFirstAvailableDoctor();
        }

        return doctorRepository.findDoctorByPreferences(patient.getDoctorPreference());
    }
}
