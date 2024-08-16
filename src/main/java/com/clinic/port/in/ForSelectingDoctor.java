package com.clinic.port.in;

import com.clinic.domain.Patient;
import com.clinic.domain.Doctor;

public interface ForSelectingDoctor {
    Doctor selectDoctorForPatient(Patient patient);
}
