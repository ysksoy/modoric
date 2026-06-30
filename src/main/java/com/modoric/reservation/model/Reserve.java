package com.modoric.reservation.model;

import java.time.LocalDateTime;

/** 会員がレッスン枠を予約した情報を表すエンティティです。 */
public class Reserve {
    private final int id;
    private final int memberId;
    private final int lessonId;
    private final LocalDateTime reservedAt;

    public Reserve(int id, int memberId, int lessonId, LocalDateTime reservedAt) {
        this.id = id;
        this.memberId = memberId;
        this.lessonId = lessonId;
        this.reservedAt = reservedAt;
    }

    public int getId() {
        return id;
    }

    public int getMemberId() {
        return memberId;
    }

    /** 既存画面・DAOの呼び出し名との互換性を保つための別名です。 */
    public int getUserId() {
        return memberId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public LocalDateTime getReservedAt() {
        return reservedAt;
    }
}
