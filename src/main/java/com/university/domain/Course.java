package com.university.domain;

public class Course {
    private final Long id;
    private final String title;
    private final boolean isAdvanced;
    private final String topic;

    public Course(Long id, String title, boolean isAdvanced, String topic) {
        this.id = id;
        this.title = title;
        this.isAdvanced = isAdvanced;
        this.topic = topic;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAdvanced() {
        return isAdvanced;
    }

    public String getTopic() {
        return topic;
    }
}


