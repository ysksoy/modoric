package com.modoric.reservation.dao;

import com.modoric.reservation.model.Reservation;
import com.modoric.reservation.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** 予約情報の存在確認・登録・取得を担当するDAOクラスです。 */
public class ReservationDAO {
    /** 指定ユーザーが指定レッスンをすでに予約しているか確認します。 */
    public boolean exists(int userId, int lessonId) throws SQLException {
        String sql = "SELECT 1 FROM reservations WHERE user_id = ? AND lesson_id = ? LIMIT 1";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, lessonId);

            try (ResultSet rs = statement.executeQuery()) {
                // 1件でも取得できれば予約済みと判断します。
                return rs.next();
            }
        }
    }

    /**
     * 予約レコードを登録し、自動採番されたIDを使って登録後の予約情報を返します。
     */
    public Reservation create(int userId, int lessonId) throws SQLException {
        String insertSql = "INSERT INTO reservations (user_id, lesson_id) VALUES (?, ?)";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
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

        throw new SQLException("予約IDの取得に失敗しました。");
    }

    /** 同じコネクションを使って、登録済み予約の詳細情報を取得します。 */
    private Reservation findById(Connection connection, int id) throws SQLException {
        String sql = "SELECT id, user_id, lesson_id, reserved_at FROM reservations WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Reservation(
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
