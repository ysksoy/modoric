<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="com.modoric.reservation.model.Lesson" %>
<%!
  private String h(String value) {
    if (value == null) {
      return "";
    }
    return value.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;");
  }
%>
<!doctype html>
<html lang="ja">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>予約内容確認 | オンラインレッスン予約</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles.css" />
  </head>
  <body>
    <main class="screen">
      <header class="site-header compact">
        <div>
          <p class="eyebrow">Reservation Confirm</p>
          <h1>予約内容確認</h1>
        </div>
      </header>

      <article class="center-panel">
        <h2>以下の内容で予約します</h2>
        <div class="reservation-list">
          <div class="info-box"><span>日時</span><strong>${lesson.formattedDate}</strong></div>
          <div class="info-box"><span>時間帯</span><strong>${lesson.timeRange}</strong></div>
          <div class="info-box"><span>レッスン名</span><strong>${lesson.title}</strong></div>
          <div class="info-box"><span>インストラクター</span><strong>${lesson.instructor}</strong></div>
        </div>
      </article>

      <div class="fixed-actions">
        <%
          String lessonListUrl = (String) request.getAttribute("lessonListUrl");
          Lesson lessonForUrl = (Lesson) request.getAttribute("lesson");
          String lessonDetailUrl = request.getContextPath() + "/lesson-detail?id=" + lessonForUrl.getId()
              + "&returnTo=" + URLEncoder.encode(lessonListUrl == null ? "" : lessonListUrl, StandardCharsets.UTF_8);
        %>
        <a class="button secondary" href="<%= h(lessonDetailUrl) %>">戻る</a>
        <form action="${pageContext.request.contextPath}/reservation-complete" method="post">
          <input type="hidden" name="returnTo" value="<%= h(lessonListUrl) %>" />
          <button type="submit" class="primary">予約確定</button>
        </form>
      </div>
    </main>
  </body>
</html>
