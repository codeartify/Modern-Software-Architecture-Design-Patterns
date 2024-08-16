package com.clinic.domain;

import com.clinic.adapter.out.persistence.entity.Gender;

public class DoctorPreference {
    private final Gender gender;
    private final String specialization;
    private final Language language;

    public DoctorPreference(Gender gender, String specialization, Language language){
        this.gender=gender;
        this.specialization=specialization;
        this.language=language;
    }
    public Gender getGender() {
        return gender;
    }

    public String getSpecialization() {
        return specialization;
    }

    public Language getLanguage() {
        return language;
    }
}
