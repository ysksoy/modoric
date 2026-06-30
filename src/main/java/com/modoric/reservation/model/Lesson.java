package com.modoric.reservation.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Lesson {
    private int id;
    private LessonCategory lessonCategory = new LessonCategory();
    private Schedule schedule = new Schedule();
    private String title;
    private Instructor instructor = new Instructor();
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

    public LessonCategory getLessonCategory() {
        return lessonCategory;
    }

    public void setLessonCategory(LessonCategory lessonCategory) {
        this.lessonCategory = lessonCategory;
    }

    /** JSPの既存プロパティ名と検索条件に合わせてカテゴリー名を返します。 */
    public String getCategory() {
        return lessonCategory.getName();
    }

    public void setCategory(String category) {
        this.lessonCategory = new LessonCategory(category);
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public LocalDate getLessonDate() {
        return schedule.getLessonDate();
    }

    public void setLessonDate(LocalDate lessonDate) {
        schedule.setLessonDate(lessonDate);
    }

    public LocalTime getStartTime() {
        return schedule.getTimeFrame().getStartTime();
    }

    public void setStartTime(LocalTime startTime) {
        schedule.getTimeFrame().setStartTime(startTime);
    }

    public LocalTime getEndTime() {
        return schedule.getTimeFrame().getEndTime();
    }

    public void setEndTime(LocalTime endTime) {
        schedule.getTimeFrame().setEndTime(endTime);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instructor getInstructorInfo() {
        return instructor;
    }

    public void setInstructorInfo(Instructor instructor) {
        this.instructor = instructor;
    }

    /** JSPの既存プロパティ名と検索条件に合わせて講師名を返します。 */
    public String getInstructor() {
        return instructor.getName();
    }

    public void setInstructor(String instructor) {
        this.instructor = new Instructor(instructor);
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
        return schedule.getFormattedDate();
    }

    public String getTimeRange() {
        return schedule.getTimeFrame().getTimeRange();
    }
}
