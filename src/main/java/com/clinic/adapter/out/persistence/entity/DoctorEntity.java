package com.clinic.adapter.out.persistence.entity;

public class DoctorEntity {
    private String id;
    private String name;
    private String specialization;
    private String language;
    private String gender;
    private boolean isAvailable;

    public DoctorEntity(String id, String name, String specialization, String language) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.language = language;
        this.isAvailable = true;
    }

    public DoctorEntity(){}

    public String getGender() {
        return gender;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getLanguage() {
        return language;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
