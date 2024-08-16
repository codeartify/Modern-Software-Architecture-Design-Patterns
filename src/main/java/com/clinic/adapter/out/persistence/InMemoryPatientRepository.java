package com.clinic.adapter.out.persistence;

import com.clinic.adapter.out.persistence.entity.DoctorEntity;
import com.clinic.adapter.out.persistence.entity.Gender;
import com.clinic.adapter.out.persistence.entity.PatientEntity;
import com.clinic.domain.DoctorPreference;
import com.clinic.domain.Language;
import com.clinic.domain.Patient;
import com.clinic.domain.Urgency;
import com.clinic.port.out.PatientRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryPatientRepository implements PatientRepository {
    private final Map<String, PatientEntity> patientStore = new HashMap<>();
    private final List<DoctorEntity> doctorStore = new ArrayList<>();


    @Override
    public void save(Patient patient) {
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
                entity.getname(),
                entity.getBirthDate(),
                entity.getPrivacyAgreement(),
                Urgency.valueOf(entity.getUrgency()
                ),
                doctorPreference
        );
    }

    private DoctorPreference findDoctorPreference(PatientEntity entity) {
        return doctorStore
                .stream()
                .filter(doctor -> doctor.getId().equals(entity.getPreferredDoctorId()))
                .findFirst()
                .map(doctor -> new DoctorPreference(Gender.valueOf(doctor.getGender()), doctor.getSpecialization(), Language.valueOf(doctor.getLanguage())))
                .orElseThrow();
    }
}
