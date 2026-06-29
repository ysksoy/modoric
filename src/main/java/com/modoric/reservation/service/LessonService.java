package com.modoric.reservation.service;

import com.modoric.reservation.dao.LessonDAO;
import com.modoric.reservation.model.Lesson;

import java.sql.SQLException;
import java.util.List;

public class LessonService {
    private static final int RECENT_LESSON_LIMIT = 20;

    private final LessonDAO lessonDAO;

    public LessonService() {
        this(new LessonDAO());
    }

    LessonService(LessonDAO lessonDAO) {
        this.lessonDAO = lessonDAO;
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
                    ? lessonDAO.search(normalizedCategory, normalizedLessonDate, normalizedInstructor)
                    : lessonDAO.findRecent(RECENT_LESSON_LIMIT);

            return new LessonSearchResult(
                    lessons,
                    lessonDAO.findCategories(),
                    lessonDAO.findInstructors(),
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
            return lessonDAO.findById(lessonId);
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
