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

/** レッスン情報の検索・詳細取得・検索条件取得を担当するDAOクラスです。 */
public class LessonDAO {
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

    /**
     * 入力された検索条件だけをWHERE句に追加し、該当するレッスン一覧を取得します。
     */
    public List<Lesson> search(String category, String lessonDate, String instructor) throws SQLException {
        // 条件が任意のため、SQL文字列とパラメーターを同じ順番で組み立てます。
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
            // 追加した検索条件の順番に合わせてプレースホルダーへ値を設定します。
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

    /** 検索フォームのカテゴリー選択肢を取得します。 */
    public List<String> findCategories() throws SQLException {
        return findDistinctValues("category");
    }

    /** 検索フォームのインストラクター選択肢を取得します。 */
    public List<String> findInstructors() throws SQLException {
        return findDistinctValues("instructor");
    }

    /** 指定カラムの重複しない値を取得し、プルダウン表示に利用します。 */
    private List<String> findDistinctValues(String columnName) throws SQLException {
        String sql = "SELECT DISTINCT " + columnName + " FROM lessons ORDER BY " + columnName;
        List<String> values = new ArrayList<>();

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                values.add(rs.getString(1));
            }
        }

        return values;
    }

    /** ResultSetの全行をLessonモデルのリストへ変換します。 */
    private List<Lesson> mapLessons(ResultSet rs) throws SQLException {
        List<Lesson> lessons = new ArrayList<>();
        while (rs.next()) {
            lessons.add(mapLesson(rs));
        }
        return lessons;
    }

    /** ResultSetの現在行をLessonモデルへ変換します。 */
    private Lesson mapLesson(ResultSet rs) throws SQLException {
        Lesson lesson = new Lesson();
        lesson.setId(rs.getInt("id"));
        lesson.setCategory(rs.getString("category"));
        lesson.setLessonDate(rs.getDate("lesson_date").toLocalDate());
        lesson.setStartTime(rs.getTime("start_time").toLocalTime());
        lesson.setEndTime(rs.getTime("end_time").toLocalTime());
        lesson.setTitle(rs.getString("title"));
        lesson.setInstructor(rs.getString("instructor"));
        lesson.setLevelName(rs.getString("level_name"));
        lesson.setCapacity(rs.getInt("capacity"));
        lesson.setDescription(rs.getString("description"));
        lesson.setStreamingId(rs.getString("streaming_id"));
        lesson.setStreamingPassword(rs.getString("streaming_password"));
        return lesson;
    }

    /** nullまたは空白だけの文字列を検索条件として扱わないための判定です。 */
    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
