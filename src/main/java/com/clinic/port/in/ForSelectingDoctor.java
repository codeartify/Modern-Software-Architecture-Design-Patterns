package com.clinic.port.in;

import com.clinic.domain.Doctor;
import com.clinic.domain.Patient;

public interface ForSelectingDoctor {
    Doctor selectDoctorForPatient(Patient patient);
}
