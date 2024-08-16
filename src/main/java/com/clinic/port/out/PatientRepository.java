package com.clinic.port.out;

import com.clinic.domain.Patient;

public interface PatientRepository {
    void save(Patient patient);

    Patient findById(String id);
}
