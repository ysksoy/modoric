package com.modoric.reservation.dao;

import com.modoric.reservation.model.Lesson;
import com.modoric.reservation.util.DBUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** レッスン情報の検索・詳細取得を担当するDAOクラスです。 */
public class LessonDAO {
    private final LessonCategoryDAO lessonCategoryDAO;
    private final ScheduleDAO scheduleDAO;
    private final InstructorDAO instructorDAO;

    public LessonDAO() {
        this(new LessonCategoryDAO(), new ScheduleDAO(), new InstructorDAO());
    }

    LessonDAO(LessonCategoryDAO lessonCategoryDAO, ScheduleDAO scheduleDAO, InstructorDAO instructorDAO) {
        this.lessonCategoryDAO = lessonCategoryDAO;
        this.scheduleDAO = scheduleDAO;
        this.instructorDAO = instructorDAO;
    }

    /** 開催日と開始時刻が近い順に、指定件数分のレッスンを取得します。 */
    public List<Lesson> findRecent(int limit) throws SQLException {
        String sql = """
                SELECT *
                FROM lessons
                ORDER BY lesson_date ASC, start_time ASC
                LIMIT ?
                """;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, limit);
            try (ResultSet rs = statement.executeQuery()) {
                return mapLessons(rs);
            }
        }
    }

    /** 入力された検索条件だけをWHERE句に追加し、該当するレッスン一覧を取得します。 */
    public List<Lesson> search(String category, String lessonDate, String instructor) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM lessons WHERE 1 = 1");
        List<Object> params = new ArrayList<>();

        if (hasText(category)) {
            sql.append(" AND category = ?");
            params.add(category);
        }
        if (hasText(lessonDate)) {
            sql.append(" AND lesson_date = ?");
            params.add(Date.valueOf(lessonDate));
        }
        if (hasText(instructor)) {
            sql.append(" AND instructor = ?");
            params.add(instructor);
        }

        sql.append(" ORDER BY lesson_date ASC, start_time ASC");

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = statement.executeQuery()) {
                return mapLessons(rs);
            }
        }
    }

    /** レッスン詳細画面や予約確認画面で使う1件分のレッスンを取得します。 */
    public Lesson findById(int id) throws SQLException {
        String sql = "SELECT * FROM lessons WHERE id = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapLesson(rs);
                }
            }
        }

        return null;
    }

    private List<Lesson> mapLessons(ResultSet rs) throws SQLException {
        List<Lesson> lessons = new ArrayList<>();
        while (rs.next()) {
            lessons.add(mapLesson(rs));
        }
        return lessons;
    }

    private Lesson mapLesson(ResultSet rs) throws SQLException {
        Lesson lesson = new Lesson();
        lesson.setId(rs.getInt("id"));
        lesson.setLessonCategory(lessonCategoryDAO.map(rs));
        lesson.setSchedule(scheduleDAO.map(rs));
        lesson.setTitle(rs.getString("title"));
        lesson.setInstructorInfo(instructorDAO.map(rs));
        lesson.setLevelName(rs.getString("level_name"));
        lesson.setCapacity(rs.getInt("capacity"));
        lesson.setDescription(rs.getString("description"));
        lesson.setStreamingId(rs.getString("streaming_id"));
        lesson.setStreamingPassword(rs.getString("streaming_password"));
        return lesson;
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
