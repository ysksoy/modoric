package com.modoric.reservation.dao;

import com.modoric.reservation.model.Reservation;
import com.modoric.reservation.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReservationDAO {
    public boolean exists(int userId, int lessonId) throws SQLException {
        String sql = "SELECT 1 FROM reservations WHERE user_id = ? AND lesson_id = ? LIMIT 1";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, lessonId);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    public Reservation create(int userId, int lessonId) throws SQLException {
        String insertSql = "INSERT INTO reservations (user_id, lesson_id) VALUES (?, ?)";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, userId);
            statement.setInt(2, lessonId);
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return findById(connection, keys.getInt(1));
                }
            }
        }

        throw new SQLException("予約IDの取得に失敗しました。");
    }

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
