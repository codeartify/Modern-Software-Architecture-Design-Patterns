package com.clinic.port.out;

import com.clinic.domain.Doctor;

public interface DoctorRepository {
    Doctor findDoctorByPreferences(String gender, String specialization, String language);

    Doctor findFirstAvailableDoctor();
}
