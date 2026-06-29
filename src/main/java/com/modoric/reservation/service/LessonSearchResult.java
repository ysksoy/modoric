package com.modoric.reservation.service;

import com.modoric.reservation.model.Lesson;

import java.util.List;

public record LessonSearchResult(
        List<Lesson> lessons,
        List<String> categories,
        List<String> instructors,
        boolean searching,
        boolean missingSearchCondition,
        String category,
        String lessonDate,
        String instructor) {
}
