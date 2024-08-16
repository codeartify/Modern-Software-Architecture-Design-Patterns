package com.clinic.port.in;

import com.clinic.domain.Patient;

public interface ForFindingPatients {
    Patient findById(String id);
}
