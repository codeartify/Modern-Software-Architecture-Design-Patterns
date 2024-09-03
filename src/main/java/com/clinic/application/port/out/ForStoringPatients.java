package com.clinic.application.port.out;

import com.clinic.domain.Patient;

public interface ForStoringPatients {
    void store(Patient patient);
}
