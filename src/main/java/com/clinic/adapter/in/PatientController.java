package com.clinic.adapter.in;

import com.clinic.adapter.in.dto.PatientDto;
import com.clinic.adapter.out.persistence.entity.Gender;
import com.clinic.domain.*;
import com.clinic.port.in.ForFindingPatients;
import com.clinic.port.in.ForRegisteringPatient;
import com.clinic.port.in.ForSelectingDoctor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/patients")
public class PatientController {
    private final ForRegisteringPatient registerPatientService;
    private final ForSelectingDoctor selectDoctorService;
    private final ForFindingPatients findingPatients;

    public PatientController(ForRegisteringPatient registerPatientService, ForSelectingDoctor selectDoctorService, ForFindingPatients findingPatients) {
        this.registerPatientService = registerPatientService;
        this.selectDoctorService = selectDoctorService;
        this.findingPatients = findingPatients;
    }

    @PostMapping
    public ResponseEntity<String> registerPatient(@RequestBody PatientDto patientDto) {
        try {
            Patient patient = toDomain(patientDto);
            registerPatientService.registerPatient(patient);
            return ResponseEntity.status(HttpStatus.CREATED).body("Patient registered successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/doctor")
    public ResponseEntity<Doctor> getDoctorForPatient(@PathVariable String id) {
        try {
            Patient patient = findingPatients.findById(id);
            if (patient == null) {
                return ResponseEntity.notFound().build();
            }
            Doctor doctor = selectDoctorService.selectDoctorForPatient(patient);
            return ResponseEntity.ok(doctor);
        } catch (IllegalArgumentException e) {
            log.error("Error selecting doctor for patient", e);
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            log.error("Error selecting doctor for patient", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public static Patient toDomain(PatientDto dto) {
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
