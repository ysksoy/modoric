package com.modoric.reservation.controller;

import com.modoric.reservation.model.Lesson;
import com.modoric.reservation.model.Reservation;
import com.modoric.reservation.model.User;
import com.modoric.reservation.service.DuplicateReservationException;
import com.modoric.reservation.service.ReservationService;
import com.modoric.reservation.service.ServiceException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/reservation-complete")
public class ReservationCompleteServlet extends HttpServlet {
    private final ReservationService reservationService = new ReservationService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionGuard.isLoggedIn(request, response)) {
            return;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        Lesson lesson = (Lesson) session.getAttribute("selectedLesson");

        if (lesson == null) {
            response.sendRedirect(request.getContextPath() + "/lessons");
            return;
        }

        try {
            Reservation reservation = reservationService.reserve(user.getId(), lesson.getId());
            request.setAttribute("lesson", lesson);
            request.setAttribute("reservation", reservation);
            session.removeAttribute("selectedLesson");
            request.getRequestDispatcher("/WEB-INF/views/reservation-complete.jsp").forward(request, response);
        } catch (DuplicateReservationException e) {
            session.removeAttribute("selectedLesson");
            request.setAttribute("lesson", lesson);
            request.setAttribute("lessonListUrl", LessonNavigation.resolveLessonListUrl(request));
            request.setAttribute("error", "このレッスンはすでに予約済みのため、予約できません。");
            request.getRequestDispatcher("/WEB-INF/views/lesson-detail.jsp").forward(request, response);
        } catch (ServiceException e) {
            throw new ServletException(e.getMessage(), e);
        }
    }
}
