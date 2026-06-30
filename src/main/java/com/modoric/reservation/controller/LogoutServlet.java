package com.modoric.reservation.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/** ログアウト処理を担当するサーブレットです。 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 既存セッションがある場合だけ破棄し、ログイン情報を削除します。
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // ログアウト後はログイン画面へ戻します。
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
