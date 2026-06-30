package com.modoric.reservation.controller;

import com.modoric.reservation.model.Member;
import com.modoric.reservation.service.AuthenticationService;
import com.modoric.reservation.service.ServiceException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/** ログイン画面の表示と認証処理を担当するサーブレットです。 */
@WebServlet({"", "/login"})
public class LoginServlet extends HttpServlet {
    private final AuthenticationService authenticationService = new AuthenticationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // GETアクセスではログインフォームを表示します。
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // POSTされた日本語を正しく扱えるように文字コードを指定します。
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // 入力された認証情報をサービス層へ渡してユーザーを確認します。
            Member member = authenticationService.authenticate(email, password);
            if (member == null) {
                request.setAttribute("error", "メールアドレスまたはパスワードが正しくありません。");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                return;
            }

            // 認証済みユーザーをセッションに保存し、以降の画面でログイン状態を判定します。
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", member);
            response.sendRedirect(request.getContextPath() + "/lessons");
        } catch (ServiceException e) {
            throw new ServletException(e.getMessage(), e);
        }
    }
}
