package com.clinic.adapter.in;

import com.clinic.adapter.in.dto.PatientDto;
import com.clinic.application.port.in.ForRegisteringPatient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PatientControllerShould {

    @Test
    void test() {
        var patientController = new PatientController(
                (ForRegisteringPatient) patient -> {

        }, null, null);

        var response = patientController.registerPatient(new PatientDto());

        assertEquals(200, response.getStatusCode());
    }
}