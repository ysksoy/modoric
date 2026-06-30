package com.modoric.reservation.service;

import com.modoric.reservation.dao.ScheduleDAO;
import com.modoric.reservation.model.Lesson;

import java.sql.SQLException;
import java.util.List;

public class LessonService {
    private static final int RECENT_LESSON_LIMIT = 20;

    private final ScheduleDAO scheduleDAO;

    public LessonService() {
        this(new ScheduleDAO());
    }

    LessonService(ScheduleDAO scheduleDAO) {
        this.scheduleDAO = scheduleDAO;
    }

    public LessonSearchResult findLessons(
            String category, String lessonDate, String instructor, boolean searchRequested) throws ServiceException {
        String normalizedCategory = normalize(category);
        String normalizedLessonDate = normalize(lessonDate);
        String normalizedInstructor = normalize(instructor);
        boolean hasSearchCondition = hasText(normalizedCategory)
                || hasText(normalizedLessonDate)
                || hasText(normalizedInstructor);

        try {
            List<Lesson> lessons = hasSearchCondition
                    ? scheduleDAO.search(normalizedCategory, normalizedLessonDate, normalizedInstructor)
                    : scheduleDAO.findRecent(RECENT_LESSON_LIMIT);

            return new LessonSearchResult(
                    lessons,
                    scheduleDAO.findCategories(),
                    scheduleDAO.findInstructors(),
                    hasSearchCondition,
                    searchRequested && !hasSearchCondition,
                    normalizedCategory,
                    normalizedLessonDate,
                    normalizedInstructor);
        } catch (SQLException | IllegalArgumentException e) {
            throw new ServiceException("レッスン一覧の取得に失敗しました。", e);
        }
    }

    public Lesson findById(int lessonId) throws ServiceException {
        try {
            return scheduleDAO.findById(lessonId);
        } catch (SQLException e) {
            throw new ServiceException("レッスン詳細の取得に失敗しました。", e);
        }
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim();
    }

    private boolean hasText(String value) {
        return !value.isBlank();
    }
}
