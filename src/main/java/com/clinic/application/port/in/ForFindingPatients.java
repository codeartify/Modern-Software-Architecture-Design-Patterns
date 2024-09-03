package com.clinic.application.port.in;

import com.clinic.domain.Patient;

public interface ForFindingPatients {
    Patient findById(String id);
}
