package com.clinic.adapter.out.persistence;

import com.clinic.adapter.out.persistence.entity.DoctorEntity;
import com.clinic.adapter.out.persistence.entity.Gender;
import com.clinic.domain.Doctor;
import com.clinic.domain.DoctorPreference;
import com.clinic.domain.Language;
import com.clinic.port.out.ForFindingDoctorByPreferences;
import com.clinic.port.out.ForFindingFirstAvailableDoctor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryDoctorRepository implements ForFindingDoctorByPreferences, ForFindingFirstAvailableDoctor {
    private final List<DoctorEntity> doctorStore = new ArrayList<>();

    @Override
    public Doctor findDoctorByPreferences(DoctorPreference doctorPreference) {
        return doctorStore.stream()
                .filter(doctor -> isPreferredDoctor(doctorPreference, doctor))
                .map(this::mapToDomain)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Doctor findFirstAvailableDoctor() {
        return doctorStore
                .stream()
                .filter(DoctorEntity::isAvailable)
                .map(this::mapToDomain)
                .findFirst()
                .orElse(mapToDomain(doctorStore.getFirst()));
    }

    private Doctor mapToDomain(DoctorEntity entity) {
        return new Doctor(
                entity.getId(),
                entity.getName(),
                entity.getSpecialization(),
                Language.valueOf(entity.getLanguage()),
                Gender.valueOf(entity.getGender())
        );
    }

    private static boolean isPreferredDoctor(DoctorPreference doctorPreference, DoctorEntity doctor) {
        return doctor.getGender().equals(doctorPreference.getGender().name()) &&
                doctor.getSpecialization().equals(doctorPreference.getSpecialization()) &&
                doctor.getLanguage().equals(doctorPreference.getLanguage().name());
    }

    public void addDoctor(Doctor doctor) {
        doctorStore.add(mapToEntity(doctor));
    }

    private DoctorEntity mapToEntity(Doctor doctor) {
        DoctorEntity entity = new DoctorEntity();
        entity.setId(doctor.getId());
        entity.setName(doctor.getName());
        entity.setSpecialization(doctor.getSpecialization());
        entity.setLanguage(doctor.getLanguage().name());
        entity.setGender(mapGender(doctor));
        return entity;
    }

    private static String mapGender(Doctor doctor) {
        return switch (doctor.getGender()) {
            case F -> "Female";
            case M -> "Male";
            case Other -> "Other";
        };
    }
}
