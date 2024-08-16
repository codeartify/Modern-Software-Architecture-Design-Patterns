package com.clinic.adapter.out.persistence;

import com.clinic.adapter.out.persistence.entity.DoctorEntity;
import com.clinic.adapter.out.persistence.entity.Gender;
import com.clinic.adapter.out.persistence.entity.PatientEntity;
import com.clinic.domain.DoctorPreference;
import com.clinic.domain.Language;
import com.clinic.domain.Patient;
import com.clinic.domain.Urgency;
import com.clinic.port.in.ForFindingPatients;
import com.clinic.port.out.ForStoringPatients;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryPatientRepository implements ForStoringPatients, ForFindingPatients {
    private final Map<String, PatientEntity> patientStore = new HashMap<>();
    private final List<DoctorEntity> doctorStore = new ArrayList<>();

    public InMemoryPatientRepository() {
        patientStore.put("1", new PatientEntity("1", "Alice", LocalDate.of(1990, 1, 1), "true", "1", "HIGH"));
        patientStore.put("2", new PatientEntity("2", "Bob", LocalDate.of(1995, 2, 2), "true", "2", "MEDIUM"));
        patientStore.put("3", new PatientEntity("3", "Charlie", LocalDate.of(2000, 3, 3), "true", "3", "LOW"));
        patientStore.put("4", new PatientEntity("4", "David", LocalDate.of(2005, 4, 4), "true", "4", "HIGH"));
        patientStore.put("5", new PatientEntity("5", "Eve", LocalDate.of(2010, 5, 5), "true", "5", "MEDIUM"));
        patientStore.put("6", new PatientEntity("6", "Frank", LocalDate.of(2015, 6, 6), "true", "6", "LOW"));

        doctorStore.add(new DoctorEntity("1", "Dr. Smith", "Cardiology", "English", "M", true));
        doctorStore.add(new DoctorEntity("2", "Dr. Johnson", "Dermatology", "Spanish", "M", true));
        doctorStore.add(new DoctorEntity("3", "Dr. Lee", "Pediatrics", "English", "F", true));
        doctorStore.add(new DoctorEntity("4", "Dr. Kim", "Cardiology", "Korean", "M", false));
        doctorStore.add(new DoctorEntity("5", "Dr. Patel", "Dermatology", "Hindi", "M", true));
        doctorStore.add(new DoctorEntity("6", "Dr. Gupta", "Pediatrics", "Hindi", "F", true));
    }

    @Override
    public void store(Patient patient) {
        DoctorEntity preferredDoctor = findPreferredDoctor(patient);
        PatientEntity entity = mapToEntity(patient, preferredDoctor);
        patientStore.put(entity.getId(), entity);
    }

    @Override
    public Patient findById(String id) {
        PatientEntity entity = patientStore.get(id);
        DoctorPreference doctorPreference = findDoctorPreference(entity);
        return entity != null ? mapToDomain(entity, doctorPreference) : null;
    }

    private PatientEntity mapToEntity(Patient patient, DoctorEntity preferredDoctor) {
        PatientEntity entity = new PatientEntity();
        entity.setId(patient.getId());
        entity.setName(patient.getName());
        entity.setBirthDate(patient.getBirthDate());
        entity.setPrivacyAgreement(patient.getPrivacyAgreement());
        entity.setPreferredDoctorId(preferredDoctor.getId()
        );
        entity.setUrgency(patient.getUrgency().name());
        return entity;
    }

    private DoctorEntity findPreferredDoctor(Patient patient) {
        return doctorStore
                .stream()
                .filter(doctor ->
                        isPreferredDoctor(patient, doctor))
                .findFirst()
                .orElseThrow();
    }

    private static boolean isPreferredDoctor(Patient patient, DoctorEntity doctor) {
        return doctor.getSpecialization().equals(patient.getDoctorPreference().getSpecialization()) &&
                doctor.getLanguage().equals(patient.getDoctorPreference().getLanguage().name())
                && doctor.getGender().equals(patient.getDoctorPreference().getGender().name());
    }

    private Patient mapToDomain(PatientEntity entity, DoctorPreference doctorPreference) {
        return new Patient(
                entity.getId(),
                entity.getName(),
                entity.getBirthDate(),
                entity.getPrivacyAgreement(),
                Urgency.valueOf(entity.getUrgency().toUpperCase()),
                doctorPreference
        );
    }

    private DoctorPreference findDoctorPreference(PatientEntity entity) {
        return doctorStore
                .stream()
                .filter(doctor -> doctor.getId().equals(entity.getPreferredDoctorId()))
                .findFirst()
                .map(doctor -> new DoctorPreference(Gender.valueOf(doctor.getGender()), doctor.getSpecialization(), Language.valueOf(doctor.getLanguage().toUpperCase())))
                .orElseThrow();
    }
}
