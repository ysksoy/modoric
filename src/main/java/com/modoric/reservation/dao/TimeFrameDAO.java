package com.modoric.reservation.dao;

import com.modoric.reservation.model.TimeFrame;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

/** レッスンの開始・終了時刻を扱うDAOクラスです。 */
public class TimeFrameDAO {
    /** lessonsテーブルの現在行から時間枠情報を生成します。 */
    TimeFrame map(ResultSet rs) throws SQLException {
        LocalTime startTime = rs.getTime("start_time").toLocalTime();
        LocalTime endTime = rs.getTime("end_time").toLocalTime();
        return new TimeFrame(startTime, endTime);
    }
}
