package com.modoric.reservation.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/** レッスンの開始・終了時刻を表す時間枠エンティティです。 */
public class TimeFrame {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private LocalTime startTime;
    private LocalTime endTime;

    public TimeFrame() {
    }

    public TimeFrame(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getTimeRange() {
        return startTime.format(TIME_FORMATTER) + "-" + endTime.format(TIME_FORMATTER);
    }

    @Override
    public String toString() {
        return "TimeFrame{"
                + "startTime=" + startTime
                + ", endTime=" + endTime
                + '}';
    }

}
