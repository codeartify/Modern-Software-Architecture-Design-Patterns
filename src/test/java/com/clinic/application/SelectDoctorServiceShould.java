package com.clinic.application;

import com.clinic.adapter.out.persistence.entity.Gender;
import com.clinic.domain.Doctor;
import com.clinic.domain.Language;
import com.clinic.domain.Patient;
import org.junit.jupiter.api.Test;

class SelectDoctorServiceShould {

    @Test
    void test() {
        var selectDoctorService = new SelectDoctorService(
                () -> new Doctor("", "", "", Language.ENGLISH, Gender.F),
                _ -> null);
 
    }

}