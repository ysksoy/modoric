package com.modoric.reservation.controller;

import com.modoric.reservation.model.Lesson;
import com.modoric.reservation.model.User;
import com.modoric.reservation.service.LessonService;
import com.modoric.reservation.service.ReservationService;
import com.modoric.reservation.service.ServiceException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/reservation-confirm")
public class ReservationConfirmServlet extends HttpServlet {
    private final LessonService lessonService = new LessonService();
    private final ReservationService reservationService = new ReservationService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionGuard.isLoggedIn(request, response)) {
            return;
        }

        try {
            int lessonId = Integer.parseInt(request.getParameter("lessonId"));
            Lesson lesson = lessonService.findById(lessonId);
            if (lesson == null) {
                response.sendRedirect(request.getContextPath() + "/lessons");
                return;
            }

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("loginUser");
            String lessonListUrl = LessonNavigation.resolveLessonListUrl(request);

            if (reservationService.isReserved(user.getId(), lesson.getId())) {
                session.removeAttribute("selectedLesson");
                request.setAttribute("lesson", lesson);
                request.setAttribute("lessonListUrl", lessonListUrl);
                request.setAttribute("error", "このレッスンはすでに予約済みのため、予約できません。");
                request.getRequestDispatcher("/WEB-INF/views/lesson-detail.jsp").forward(request, response);
                return;
            }

            session.setAttribute("selectedLesson", lesson);
            request.setAttribute("lesson", lesson);
            request.setAttribute("lessonListUrl", lessonListUrl);
            request.getRequestDispatcher("/WEB-INF/views/reservation-confirm.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/lessons");
        } catch (ServiceException e) {
            throw new ServletException(e.getMessage(), e);
        }
    }
}
