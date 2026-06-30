package com.modoric.reservation.dao;

import com.modoric.reservation.model.LessonCategory;
import com.modoric.reservation.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** レッスンカテゴリー情報の取得を担当するDAOクラスです。 */
public class LessonCategoryDAO {
    /** 検索フォームのカテゴリー選択肢を取得します。 */
    public List<String> findNames() throws SQLException {
        String sql = "SELECT DISTINCT category FROM lessons ORDER BY category";
        List<String> categories = new ArrayList<>();

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        }

        return categories;
    }

    /** lessonsテーブルの現在行からカテゴリー情報を生成します。 */
    LessonCategory map(ResultSet rs) throws SQLException {
        return new LessonCategory(rs.getString("category"));
    }
}
