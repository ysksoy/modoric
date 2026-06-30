package com.modoric.reservation.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/** レッスンの開催日と時間枠を表すエンティティです。 */
public class Schedule {
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy年M月d日(E)", Locale.JAPANESE);

    private LocalDate lessonDate;
    private TimeFrame timeFrame = new TimeFrame();

    public Schedule() {
    }

    public Schedule(LocalDate lessonDate, TimeFrame timeFrame) {
        this.lessonDate = lessonDate;
        this.timeFrame = timeFrame;
    }

    public LocalDate getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(LocalDate lessonDate) {
        this.lessonDate = lessonDate;
    }

    public TimeFrame getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(TimeFrame timeFrame) {
        this.timeFrame = timeFrame;
    }

    public String getFormattedDate() {
        return lessonDate.format(DATE_FORMATTER);
    }

    @Override
    public String toString() {
        return "Schedule{"
                + "lessonDate=" + lessonDate
                + ", timeFrame=" + timeFrame
                + '}';
    }

}
