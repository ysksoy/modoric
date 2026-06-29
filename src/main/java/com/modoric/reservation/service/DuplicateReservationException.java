package com.modoric.reservation.service;

public class DuplicateReservationException extends ServiceException {
    public DuplicateReservationException(Throwable cause) {
        super("このレッスンはすでに予約済みです。", cause);
    }
}
