package com.clinic.application.port.in;

import com.clinic.domain.Doctor;
import com.clinic.domain.Patient;

public interface ForSelectingDoctor {
    Doctor selectDoctorForPatient(Patient patient);
}
