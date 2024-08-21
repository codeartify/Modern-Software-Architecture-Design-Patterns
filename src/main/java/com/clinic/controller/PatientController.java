package com.clinic.controller;

import com.clinic.controller.dto.PatientDto;
import com.clinic.db.entity.Gender;
import com.clinic.domain.*;
import com.clinic.interfaces.ForFindingPatients;
import com.clinic.interfaces.ForRegisteringPatient;
import com.clinic.interfaces.ForSelectingDoctor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/patients")
public class PatientController {

    @PostMapping
    public ResponseEntity<String> registerPatient(@RequestBody PatientDto patientDto) {
        try {
            Patient patient = toDomain(patientDto);
            // TODO: call the service to register the patient
            return ResponseEntity.status(HttpStatus.CREATED).body("Patient registered successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/doctor")
    public ResponseEntity<Doctor> getDoctorForPatient(@PathVariable("id") String id) {
        try {
           // TODO  call the service to get the doctor for the patient
            return ResponseEntity.ok(null);
        } catch (IllegalArgumentException e) {
            log.error("Error selecting doctor for patient", e);
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            log.error("Error selecting doctor for patient", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private static Patient toDomain(PatientDto dto) {
        return new Patient(
                dto.getId(),
                dto.getName(),
                dto.getBirthDate(),
                dto.getPrivacyAgreement(),
                Urgency.valueOf(dto.getUrgency().name()),
                new DoctorPreference(
                        Gender.valueOf(dto.getGender().toUpperCase()),
                        dto.getSpecialization(),
                        Language.valueOf(dto.getLanguage().toUpperCase())
                )
        );
    }
}
