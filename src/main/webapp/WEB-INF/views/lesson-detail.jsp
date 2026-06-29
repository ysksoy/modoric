<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="ja">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>${lesson.title} | オンラインレッスン予約</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles.css" />
  </head>
  <body>
    <main class="screen">
      <header class="site-header compact">
        <div>
          <p class="eyebrow">Lesson Detail</p>
          <h1>${lesson.title}</h1>
        </div>
      </header>

      <p class="error">${error}</p>
      <article class="detail-panel">
        <div class="detail-summary">
          <div class="info-box"><span>日時</span><strong>${lesson.formattedDate}</strong></div>
          <div class="info-box"><span>時間帯</span><strong>${lesson.timeRange}</strong></div>
          <div class="info-box"><span>レッスン名</span><strong>${lesson.title}</strong></div>
          <div class="info-box"><span>インストラクター</span><strong>${lesson.instructor}</strong></div>
        </div>
        <div class="info-box">
          <span>レッスン概要</span>
          <strong>${lesson.description}</strong>
        </div>
        <div class="info-box">
          <span>カテゴリー / レベル / 定員</span>
          <strong>${lesson.category} / ${lesson.levelName} / ${lesson.capacity}名</strong>
        </div>
      </article>

      <div class="fixed-actions">
        <a class="button secondary" href="<c:out value="${lessonListUrl}" />">戻る</a>
        <form action="${pageContext.request.contextPath}/reservation-confirm" method="post">
          <input type="hidden" name="lessonId" value="${lesson.id}" />
          <input type="hidden" name="returnTo" value="<c:out value="${lessonListUrl}" />" />
          <button type="submit" class="primary">予約する</button>
        </form>
      </div>
    </main>
  </body>
</html>
