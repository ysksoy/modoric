package com.modoric.reservation.dao;

import com.modoric.reservation.model.User;
import com.modoric.reservation.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** ユーザー情報をデータベースから取得するDAOクラスです。 */
public class UserDAO {
    /**
     * 入力されたメールアドレスとパスワードに一致するユーザーを検索します。
     * 一致するレコードがない場合は、ログイン失敗を表すためにnullを返します。
     */
    public User authenticate(String email, String password) throws SQLException {
        String sql = "SELECT id, name, email FROM users WHERE email = ? AND password = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            // プレースホルダーに値を設定し、SQLインジェクションを防ぎます。
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet rs = statement.executeQuery()) {
                // 認証に必要な最小限のユーザー情報だけをモデルへ詰め替えます。
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email")
                    );
                }
            }
        }

        return null;
    }
}
