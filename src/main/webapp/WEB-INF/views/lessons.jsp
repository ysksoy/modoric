<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="java.util.List" %>
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
    <title>レッスン一覧 | オンラインレッスン予約</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles.css" />
  </head>
  <body>
    <main class="screen">
      <header class="site-header">
        <div>
          <p class="eyebrow">Lesson Search</p>
          <h1>オンラインレッスン一覧</h1>
        </div>
        <form class="header-form" action="${pageContext.request.contextPath}/logout" method="post">
          <button type="submit" class="ghost">ログアウト</button>
        </form>
      </header>

      <form class="search-area" action="${pageContext.request.contextPath}/lessons" method="get" aria-label="レッスン検索">
        <input type="hidden" name="searched" value="true" />
        <label>
          カテゴリー
          <select name="category">
            <option value="">選択してください</option>
            <%
              List<String> categories = (List<String>) request.getAttribute("categories");
              String selectedCategory = (String) request.getAttribute("category");
              if (categories != null) {
                for (String item : categories) {
            %>
              <option value="<%= h(item) %>" <%= item.equals(selectedCategory) ? "selected" : "" %>><%= h(item) %></option>
            <%
                }
              }
            %>
          </select>
        </label>
        <label>
          日時
          <input name="lessonDate" type="date" value="${lessonDate}" />
        </label>
        <label>
          インストラクター
          <select name="instructor">
            <option value="">選択してください</option>
            <%
              List<String> instructors = (List<String>) request.getAttribute("instructors");
              String selectedInstructor = (String) request.getAttribute("instructor");
              if (instructors != null) {
                for (String item : instructors) {
            %>
              <option value="<%= h(item) %>" <%= item.equals(selectedInstructor) ? "selected" : "" %>><%= h(item) %></option>
            <%
                }
              }
            %>
          </select>
        </label>
        <button type="submit" class="primary">検索</button>
      </form>
      <p class="empty error">${error}</p>

      <section>
        <div class="section-title">
          <%
            List<Lesson> lessons = (List<Lesson>) request.getAttribute("lessons");
            boolean searching = Boolean.TRUE.equals(request.getAttribute("searching"));
            int lessonCount = lessons == null ? 0 : lessons.size();
          %>
          <h2><%= searching ? "検索結果" : "直近のレッスン" %></h2>
          <span class="lesson-count"><%= lessonCount %>件</span>
        </div>

        <% if (lessons == null || lessons.isEmpty()) { %>
          <p class="empty">該当するレッスンはありません。</p>
        <% } else { %>
          <div class="lesson-grid">
            <%
              String contextPath = request.getContextPath();
              String lessonListUrl = contextPath + "/lessons";
              if (searching) {
                String category = (String) request.getAttribute("category");
                String lessonDate = (String) request.getAttribute("lessonDate");
                String instructor = (String) request.getAttribute("instructor");
                lessonListUrl += "?searched=true"
                    + "&category=" + URLEncoder.encode(category == null ? "" : category, StandardCharsets.UTF_8)
                    + "&lessonDate=" + URLEncoder.encode(lessonDate == null ? "" : lessonDate, StandardCharsets.UTF_8)
                    + "&instructor=" + URLEncoder.encode(instructor == null ? "" : instructor, StandardCharsets.UTF_8);
              }
              for (Lesson lesson : lessons) {
                String lessonDetailUrl = contextPath + "/lesson-detail?id=" + lesson.getId()
                    + "&returnTo=" + URLEncoder.encode(lessonListUrl, StandardCharsets.UTF_8);
            %>
              <a class="lesson-card" href="<%= h(lessonDetailUrl) %>">
                <span class="badge"><%= h(lesson.getCategory()) %></span>
                <h3><%= h(lesson.getTitle()) %></h3>
                <div class="lesson-meta">
                  <span><%= h(lesson.getFormattedDate()) %> <%= h(lesson.getTimeRange()) %></span>
                  <span>講師：<%= h(lesson.getInstructor()) %></span>
                  <span>レベル：<%= h(lesson.getLevelName()) %> / 定員：<%= lesson.getCapacity() %>名</span>
                </div>
              </a>
            <% } %>
          </div>
        <% } %>
      </section>
    </main>
  </body>
</html>
