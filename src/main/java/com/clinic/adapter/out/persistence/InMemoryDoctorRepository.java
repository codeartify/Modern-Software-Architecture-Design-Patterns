package com.clinic.adapter.out.persistence;

import com.clinic.adapter.out.persistence.entity.DoctorEntity;
import com.clinic.adapter.out.persistence.entity.Gender;
import com.clinic.domain.Doctor;
import com.clinic.domain.Language;
import com.clinic.port.out.DoctorRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryDoctorRepository implements DoctorRepository {
    private final List<DoctorEntity> doctors = new ArrayList<>();

    @Override
    public Doctor findDoctorByPreferences(String gender, String specialization, String language) {
        return doctors.stream()
                .filter(doctor -> isPreferredDoctor(gender, specialization, language, doctor))
                .map(this::mapToDomain)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Doctor findFirstAvailableDoctor() {
        return doctors
                .stream()
                .filter(DoctorEntity::isAvailable)
                .map(this::mapToDomain)
                .findFirst()
                .orElse(mapToDomain(doctors.getFirst()));
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

    private static boolean isPreferredDoctor(String gender, String specialization, String language, DoctorEntity doctor) {
        return doctor.getGender().equals(gender) &&
                doctor.getSpecialization().equals(specialization) &&
                doctor.getLanguage().equals(language);
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(mapToEntity(doctor));
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
