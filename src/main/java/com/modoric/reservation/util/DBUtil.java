package com.modoric.reservation.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBUtil {
    private static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DEFAULT_URL =
            "jdbc:mysql://localhost:3306/lesson_reservation?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Tokyo";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "";

    private DBUtil() {
    }

    public static Connection getConnection() throws SQLException {
        loadDriver();
        String url = getEnv("DB_URL", DEFAULT_URL);
        String user = getEnv("DB_USER", DEFAULT_USER);
        String password = getEnv("DB_PASSWORD", DEFAULT_PASSWORD);
        return DriverManager.getConnection(url, user, password);
    }

    private static void loadDriver() throws SQLException {
        try {
            Class.forName(MYSQL_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBCドライバが見つかりません。", e);
        }
    }

    private static String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value;
    }
}
