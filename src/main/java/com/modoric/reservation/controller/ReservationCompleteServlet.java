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

/** 予約登録を実行し、完了画面を表示するサーブレットです。 */
@WebServlet("/reservation-complete")
public class ReservationCompleteServlet extends HttpServlet {
    private final ReservationService reservationService = new ReservationService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 未ログイン状態で予約登録されないように確認します。
        if (!SessionGuard.isLoggedIn(request, response)) {
            return;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        Lesson lesson = (Lesson) session.getAttribute("selectedLesson");

        // 確認画面を経由していない場合は予約対象がないため、一覧へ戻します。
        if (lesson == null) {
            response.sendRedirect(request.getContextPath() + "/lessons");
            return;
        }

        try {
            // サービス層で重複確認を行ったうえで予約を登録します。
            Reservation reservation = reservationService.reserve(user.getId(), lesson.getId());
            request.setAttribute("lesson", lesson);
            request.setAttribute("reservation", reservation);
            // 登録後は再送信で同じ予約が作られないよう、選択中レッスンを削除します。
            session.removeAttribute("selectedLesson");
            request.getRequestDispatcher("/WEB-INF/views/reservation-complete.jsp").forward(request, response);
        } catch (DuplicateReservationException e) {
            // 他画面や再送信で先に予約済みになった場合は詳細画面にエラーを表示します。
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
