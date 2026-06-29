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

@WebServlet("/lessons")
public class LessonListServlet extends HttpServlet {
    private final LessonService lessonService = new LessonService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionGuard.isLoggedIn(request, response)) {
            return;
        }

        boolean searchRequested = request.getParameter("searched") != null;

        try {
            LessonSearchResult result = lessonService.findLessons(
                    request.getParameter("category"),
                    request.getParameter("lessonDate"),
                    request.getParameter("instructor"),
                    searchRequested);

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
