<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="ja">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>予約完了 | オンラインレッスン予約</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles.css" />
  </head>
  <body>
    <main class="screen">
      <header class="site-header compact">
        <div>
          <p class="eyebrow">Reservation Complete</p>
          <h1>予約が完了しました</h1>
        </div>
      </header>

      <article class="center-panel">
        <h2>オンライン参加情報</h2>
        <div class="reservation-list">
          <div class="info-box"><span>日時</span><strong>${lesson.formattedDate}</strong></div>
          <div class="info-box"><span>時間帯</span><strong>${lesson.timeRange}</strong></div>
          <div class="info-box"><span>レッスン名</span><strong>${lesson.title}</strong></div>
          <div class="info-box"><span>インストラクター</span><strong>${lesson.instructor}</strong></div>
          <div class="info-box"><span>ストリーミングID</span><strong>${lesson.streamingId}</strong></div>
          <div class="info-box"><span>ストリーミングパスワード</span><strong>${lesson.streamingPassword}</strong></div>
        </div>
      </article>

      <div class="center-actions">
        <a class="button primary" href="${pageContext.request.contextPath}/lessons">トップページへ戻る</a>
      </div>
    </main>
  </body>
</html>
