package com.modoric.reservation.dao;

import com.modoric.reservation.model.Reserve;
import com.modoric.reservation.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

/** 予約情報の存在確認・登録・取得を担当するDAOクラスです。 */
public class ReserveDAO {
    private static final String EXISTS_SAME_SCHEDULE_SQL = """
            SELECT 1
            FROM reservations r
            JOIN lessons reserved_lesson ON reserved_lesson.id = r.lesson_id
            JOIN lessons target_lesson ON target_lesson.id = ?
            WHERE r.user_id = ?
              AND reserved_lesson.lesson_date = target_lesson.lesson_date
              AND reserved_lesson.start_time = target_lesson.start_time
              AND reserved_lesson.end_time = target_lesson.end_time
            LIMIT 1
            """;

    /** 指定ユーザーが同じ日付・時間帯のレッスンをすでに予約しているか確認します。 */
    public boolean exists(int userId, int lessonId) throws SQLException {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(EXISTS_SAME_SCHEDULE_SQL)) {
            statement.setInt(1, lessonId);
            statement.setInt(2, userId);

            try (ResultSet rs = statement.executeQuery()) {
                // 会員番号・日付・時間帯が完全一致する予約が1件でもあれば重複と判断します。
                return rs.next();
            }
        }
    }

    /**
     * 予約レコードを登録し、自動採番されたIDを使って登録後の予約情報を返します。
     */
    public Reserve create(int userId, int lessonId) throws SQLException {
        String insertSql = "INSERT INTO reservations (user_id, lesson_id) VALUES (?, ?)";

        try (Connection connection = DBUtil.getConnection()) {
            if (exists(connection, userId, lessonId)) {
                throw new SQLIntegrityConstraintViolationException("同じ日付・時間帯の予約がすでに存在します。");
            }

            try (PreparedStatement statement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, userId);
                statement.setInt(2, lessonId);
                statement.executeUpdate();

                // DBが採番した予約IDを取得し、画面表示に使う予約日時を含めて読み直します。
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (keys.next()) {
                        return findById(connection, keys.getInt(1));
                    }
                }
            }
        }

        throw new SQLException("予約IDの取得に失敗しました。");
    }

    /** 同じコネクションを使って、同じ日付・時間帯の予約が存在するか確認します。 */
    private boolean exists(Connection connection, int userId, int lessonId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(EXISTS_SAME_SCHEDULE_SQL)) {
            statement.setInt(1, lessonId);
            statement.setInt(2, userId);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    /** 同じコネクションを使って、登録済み予約の詳細情報を取得します。 */
    private Reserve findById(Connection connection, int id) throws SQLException {
        String sql = "SELECT id, user_id, lesson_id, reserved_at FROM reservations WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Reserve(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getInt("lesson_id"),
                            rs.getTimestamp("reserved_at").toLocalDateTime()
                    );
                }
            }
        }

        throw new SQLException("予約情報が見つかりません。");
    }
}
