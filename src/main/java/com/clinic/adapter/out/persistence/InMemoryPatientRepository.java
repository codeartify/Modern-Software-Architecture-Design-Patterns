package com.clinic.adapter.out.persistence;

import com.clinic.adapter.out.persistence.entity.PatientEntity;
import com.clinic.domain.Patient;
import com.clinic.domain.Urgency;
import com.clinic.port.out.PatientRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryPatientRepository implements PatientRepository {
    private final Map<String, PatientEntity> patientStore = new HashMap<>();

    @Override
    public void save(Patient patient) {
        PatientEntity entity = mapToEntity(patient);
        patientStore.put(entity.getId(), entity);
    }

    @Override
    public Patient findById(String id) {
        PatientEntity entity = patientStore.get(id);
        return entity != null ? mapToDomain(entity) : null;
    }

    private PatientEntity mapToEntity(Patient patient) {
        PatientEntity entity = new PatientEntity();
        entity.setId(patient.getId());
        entity.setName(patient.getName());
        entity.setBirthDate(patient.getBirthDate());
        entity.setPrivacyAgreement(patient.getPrivacyAgreement());
        entity.setPreferredDoctorId(patient.getPreferredDoctorId());
        entity.setUrgency(patient.getUrgency().name());
        return entity;
    }

    private Patient mapToDomain(PatientEntity entity) {
        return new Patient(
                entity.getId(),
                entity.getname(),
                entity.getBirthDate(),
                entity.getPrivacyAgreement(),
                entity.getPreferredDoctorId(),
                Urgency.valueOf(entity.getUrgency()
                )
        );
    }
}
