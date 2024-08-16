package com.clinic.domain;

import lombok.Getter;

@Getter
public enum Language {
    GERMAN("German"), ENGLISH("English"), ITALIAN("Italian"), FRENCH("French"), SPANISH("Spanish");

    private final String value;

    Language(String value) {
        this.value = value;
    }

}
