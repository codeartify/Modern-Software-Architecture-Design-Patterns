package com.clinic.port.out;

import com.clinic.domain.Doctor;
import com.clinic.domain.DoctorPreference;

public interface ForFindingDoctorByPreferences {
    Doctor findDoctorByPreferences(DoctorPreference doctorPreference);

}
