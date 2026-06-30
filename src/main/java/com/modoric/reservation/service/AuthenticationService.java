package com.modoric.reservation.service;

import com.modoric.reservation.dao.UserDAO;
import com.modoric.reservation.model.Member;

import java.sql.SQLException;

public class AuthenticationService {
    private final UserDAO userDAO;

    public AuthenticationService() {
        this(new UserDAO());
    }

    AuthenticationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Member authenticate(String email, String password) throws ServiceException {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            return null;
        }

        try {
            return userDAO.authenticate(email.trim(), password);
        } catch (SQLException e) {
            throw new ServiceException("ログイン処理に失敗しました。", e);
        }
    }
}
