package com.clinic.domain;

import com.clinic.adapter.out.persistence.entity.Gender;

public class Doctor {
    private final String id;
    private final String name;
    private final String specialization;
    private final Language language;
    private final Gender gender;

    public Doctor(String id, String name, String specialization, Language language, Gender gender) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.language = language;
        this.gender=gender;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public Language getLanguage() {
        return language;
    }

    public Gender getGender() {
        return gender;
    }
}
