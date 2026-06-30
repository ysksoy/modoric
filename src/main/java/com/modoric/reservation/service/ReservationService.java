package com.modoric.reservation.service;

import com.modoric.reservation.dao.ReserveDAO;
import com.modoric.reservation.model.Reservation;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLException;

public class ReservationService {
    private final ReserveDAO reserveDAO;

    public ReservationService() {
        this(new ReserveDAO());
    }

    ReservationService(ReserveDAO reserveDAO) {
        this.reserveDAO = reserveDAO;
    }

    public boolean isReserved(int userId, int lessonId) throws ServiceException {
        try {
            return reserveDAO.exists(userId, lessonId);
        } catch (SQLException e) {
            throw new ServiceException("予約状況の確認に失敗しました。", e);
        }
    }

    public Reservation reserve(int userId, int lessonId)
            throws DuplicateReservationException, ServiceException {
        try {
            return reserveDAO.create(userId, lessonId);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateReservationException(e);
        } catch (SQLException e) {
            throw new ServiceException("予約確定処理に失敗しました。", e);
        }
    }
}
