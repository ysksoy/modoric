<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
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
            <c:forEach var="item" items="${categories}">
              <option value="${item}" ${item == category ? 'selected' : ''}>${item}</option>
            </c:forEach>
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
            <c:forEach var="item" items="${instructors}">
              <option value="${item}" ${item == instructor ? 'selected' : ''}>${item}</option>
            </c:forEach>
          </select>
        </label>
        <button type="submit" class="primary">検索</button>
      </form>
      <p class="empty error">${error}</p>

      <section>
        <div class="section-title">
          <h2>${searching ? '検索結果' : '直近のレッスン'}</h2>
          <span class="lesson-count">${fn:length(lessons)}件</span>
        </div>

        <c:choose>
          <c:when test="${empty lessons}">
            <p class="empty">該当するレッスンはありません。</p>
          </c:when>
          <c:otherwise>
            <div class="lesson-grid">
              <c:url var="lessonListUrl" value="/lessons">
                <c:if test="${searching}">
                  <c:param name="searched" value="true" />
                  <c:param name="category" value="${category}" />
                  <c:param name="lessonDate" value="${lessonDate}" />
                  <c:param name="instructor" value="${instructor}" />
                </c:if>
              </c:url>
              <c:forEach var="lesson" items="${lessons}">
                <c:url var="lessonDetailUrl" value="/lesson-detail">
                  <c:param name="id" value="${lesson.id}" />
                  <c:param name="returnTo" value="${lessonListUrl}" />
                </c:url>
                <a class="lesson-card" href="<c:out value="${lessonDetailUrl}" />">
                  <span class="badge">${lesson.category}</span>
                  <h3>${lesson.title}</h3>
                  <div class="lesson-meta">
                    <span>${lesson.formattedDate} ${lesson.timeRange}</span>
                    <span>講師：${lesson.instructor}</span>
                    <span>レベル：${lesson.levelName} / 定員：${lesson.capacity}名</span>
                  </div>
                </a>
              </c:forEach>
            </div>
          </c:otherwise>
        </c:choose>
      </section>
    </main>
  </body>
</html>
