package com.modoric.reservation.controller;

import jakarta.servlet.http.HttpServletRequest;

final class LessonNavigation {
    private LessonNavigation() {
    }

    static String resolveLessonListUrl(HttpServletRequest request) {
        String defaultUrl = request.getContextPath() + "/lessons";
        String returnTo = request.getParameter("returnTo");

        if (returnTo != null && (returnTo.equals(defaultUrl) || returnTo.startsWith(defaultUrl + "?"))) {
            return returnTo;
        }

        return defaultUrl;
    }
}
