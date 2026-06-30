package com.modoric.reservation.controller;

import com.modoric.reservation.service.LessonSearchResult;
import com.modoric.reservation.service.LessonService;
import com.modoric.reservation.service.ServiceException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/** レッスン一覧と検索結果を表示するサーブレットです。 */
@WebServlet("/lessons")
public class LessonListServlet extends HttpServlet {
    private final LessonService lessonService = new LessonService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // レッスン一覧はログイン済みユーザーだけに表示します。
        if (!SessionGuard.isLoggedIn(request, response)) {
            return;
        }

        // 検索ボタン経由か初期表示かを判定し、未入力検索のエラー表示に使います。
        boolean searchRequested = request.getParameter("searched") != null;

        try {
            // リクエストの検索条件をサービスへ渡し、一覧・選択肢・検索状態をまとめて取得します。
            LessonSearchResult result = lessonService.findLessons(
                    request.getParameter("category"),
                    request.getParameter("lessonDate"),
                    request.getParameter("instructor"),
                    searchRequested);

            // JSPで表示する一覧データ、検索条件、エラーメッセージをリクエストに詰めます。
            request.setAttribute("lessons", result.lessons());
            request.setAttribute("categories", result.categories());
            request.setAttribute("instructors", result.instructors());
            request.setAttribute("searching", result.searching());
            if (result.missingSearchCondition()) {
                request.setAttribute("error", "カテゴリー、日時、インストラクターのいずれかを選択してください。");
            }
            request.setAttribute("category", result.category());
            request.setAttribute("lessonDate", result.lessonDate());
            request.setAttribute("instructor", result.instructor());
            request.getRequestDispatcher("/WEB-INF/views/lessons.jsp").forward(request, response);
        } catch (ServiceException e) {
            throw new ServletException(e.getMessage(), e);
        }
    }
}
