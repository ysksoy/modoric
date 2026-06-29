package com.modoric.reservation.model;

import java.time.LocalDateTime;

public class Reservation {
    private final int id;
    private final int userId;
    private final int lessonId;
    private final LocalDateTime reservedAt;

    public Reservation(int id, int userId, int lessonId, LocalDateTime reservedAt) {
        this.id = id;
        this.userId = userId;
        this.lessonId = lessonId;
        this.reservedAt = reservedAt;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public LocalDateTime getReservedAt() {
        return reservedAt;
    }
}
