package com.modoric.reservation.dao;

import com.modoric.reservation.model.Schedule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/** レッスンの開催日と時間枠を扱うDAOクラスです。 */
public class ScheduleDAO {
    private final TimeFrameDAO timeFrameDAO;

    public ScheduleDAO() {
        this(new TimeFrameDAO());
    }

    ScheduleDAO(TimeFrameDAO timeFrameDAO) {
        this.timeFrameDAO = timeFrameDAO;
    }

    /** lessonsテーブルの現在行からスケジュール情報を生成します。 */
    Schedule map(ResultSet rs) throws SQLException {
        LocalDate lessonDate = rs.getDate("lesson_date").toLocalDate();
        return new Schedule(lessonDate, timeFrameDAO.map(rs));
    }
}
