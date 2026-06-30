package com.modoric.reservation.dao;

import com.modoric.reservation.model.Instructor;
import com.modoric.reservation.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** インストラクター情報の取得を担当するDAOクラスです。 */
public class InstructorDAO {
    /** 検索フォームのインストラクター選択肢を取得します。 */
    public List<String> findNames() throws SQLException {
        String sql = "SELECT DISTINCT instructor FROM lessons ORDER BY instructor";
        List<String> instructors = new ArrayList<>();

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                instructors.add(rs.getString("instructor"));
            }
        }

        return instructors;
    }

    /** lessonsテーブルの現在行からインストラクター情報を生成します。 */
    Instructor map(ResultSet rs) throws SQLException {
        return new Instructor(rs.getString("instructor"));
    }
}
