package com.clinic.port.out;

import com.clinic.domain.Patient;

public interface ForFindingPatients {
    Patient findById(String id);
}
