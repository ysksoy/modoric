package com.modoric.reservation.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Lesson {
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy年M月d日(E)", Locale.JAPANESE);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private int id;
    private String category;
    private LocalDate lessonDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String title;
    private String instructor;
    private String levelName;
    private int capacity;
    private String description;
    private String streamingId;
    private String streamingPassword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(LocalDate lessonDate) {
        this.lessonDate = lessonDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreamingId() {
        return streamingId;
    }

    public void setStreamingId(String streamingId) {
        this.streamingId = streamingId;
    }

    public String getStreamingPassword() {
        return streamingPassword;
    }

    public void setStreamingPassword(String streamingPassword) {
        this.streamingPassword = streamingPassword;
    }

    public String getFormattedDate() {
        return lessonDate.format(DATE_FORMATTER);
    }

    public String getTimeRange() {
        return startTime.format(TIME_FORMATTER) + "-" + endTime.format(TIME_FORMATTER);
    }
}
