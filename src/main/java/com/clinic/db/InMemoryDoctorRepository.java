package com.clinic.db;

import com.clinic.db.entity.DoctorEntity;
import com.clinic.db.entity.Gender;
import com.clinic.domain.Doctor;
import com.clinic.domain.DoctorPreference;
import com.clinic.domain.Language;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryDoctorRepository  {
    private final List<DoctorEntity> doctorStore = new ArrayList<>();

    public InMemoryDoctorRepository() {
        doctorStore.add(new DoctorEntity("1", "Dr. Smith", "Cardiology", "English", "M", true));
        doctorStore.add(new DoctorEntity("2", "Dr. Johnson", "Dermatology", "Spanish", "M", true));
        doctorStore.add(new DoctorEntity("3", "Dr. Lee", "Pediatrics", "English", "F", true));
        doctorStore.add(new DoctorEntity("4", "Dr. Kim", "Cardiology", "Korean", "M", false));
        doctorStore.add(new DoctorEntity("5", "Dr. Patel", "Dermatology", "Hindi", "M", true));
        doctorStore.add(new DoctorEntity("6", "Dr. Gupta", "Pediatrics", "Hindi", "F", true));
    }


    public Doctor findDoctorByPreferences(DoctorPreference doctorPreference) {
        return doctorStore.stream()
                .filter(doctor -> isPreferredDoctor(doctorPreference, doctor))
                .map(this::mapToDomain)
                .findFirst()
                .orElse(null);
    }


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
                Language.valueOf(entity.getLanguage().toUpperCase()),
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
        entity.setGender(doctor.getGender().name());
        return entity;
    }
}
