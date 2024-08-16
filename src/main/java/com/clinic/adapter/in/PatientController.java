package com.clinic.adapter.in;

import com.clinic.domain.Doctor;
import com.clinic.domain.Patient;
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
    private final ForRegisteringPatient registerPatientUseCase;
    private final ForSelectingDoctor selectDoctorUseCase;
    private final ForFindingPatients findPatientUseCase;

    public PatientController(ForRegisteringPatient registerPatientUseCase, ForSelectingDoctor selectDoctorUseCase, ForFindingPatients findingPatients) {
        this.registerPatientUseCase = registerPatientUseCase;
        this.selectDoctorUseCase = selectDoctorUseCase;
        this.findPatientUseCase = findingPatients;
    }

    @PostMapping
    public ResponseEntity<String> registerPatient(@RequestBody Patient patient) {
        try {
            registerPatientUseCase.registerPatient(patient);
            return ResponseEntity.status(HttpStatus.CREATED).body("Patient registered successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/doctor")
    public ResponseEntity<Doctor> getDoctorForPatient(@PathVariable String id) {
        try {
            Patient patient = findPatientUseCase.findById(id);
            if (patient == null) {
                return ResponseEntity.notFound().build();
            }
            Doctor doctor = selectDoctorUseCase.selectDoctorForPatient(patient);
            return ResponseEntity.ok(doctor);
        } catch (IllegalArgumentException e) {
            log.error("Error selecting doctor for patient", e);
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            log.error("Error selecting doctor for patient", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
