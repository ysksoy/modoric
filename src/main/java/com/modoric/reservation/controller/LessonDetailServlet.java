package com.modoric.reservation.controller;

import com.modoric.reservation.model.Lesson;
import com.modoric.reservation.service.LessonService;
import com.modoric.reservation.service.ServiceException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/** レッスン詳細画面を表示するサーブレットです。 */
@WebServlet("/lesson-detail")
public class LessonDetailServlet extends HttpServlet {
    private final LessonService lessonService = new LessonService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 未ログインの場合はログイン画面へ遷移し、以降の処理を止めます。
        if (!SessionGuard.isLoggedIn(request, response)) {
            return;
        }

        try {
            // URLパラメーターのレッスンIDを使って、表示対象のレッスンを取得します。
            int lessonId = Integer.parseInt(request.getParameter("id"));
            Lesson lesson = lessonService.findById(lessonId);
            if (lesson == null) {
                response.sendRedirect(request.getContextPath() + "/lessons");
                return;
            }

            // JSPで利用するレッスン情報と一覧へ戻るURLをリクエストに保存します。
            request.setAttribute("lesson", lesson);
            request.setAttribute("lessonListUrl", LessonNavigation.resolveLessonListUrl(request));
            request.getRequestDispatcher("/WEB-INF/views/lesson-detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            // 不正なIDが指定された場合は一覧へ戻します。
            response.sendRedirect(request.getContextPath() + "/lessons");
        } catch (ServiceException e) {
            throw new ServletException(e.getMessage(), e);
        }
    }
}
