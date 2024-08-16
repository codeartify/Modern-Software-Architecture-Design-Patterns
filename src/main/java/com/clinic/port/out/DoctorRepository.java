package com.clinic.port.out;

import com.clinic.domain.Doctor;
import com.clinic.domain.DoctorPreference;

public interface DoctorRepository {
    Doctor findFirstAvailableDoctor();

    Doctor findDoctorByPreferences(DoctorPreference doctorPreference);
}
