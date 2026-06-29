<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="ja">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>ログイン | オンラインレッスン予約</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles.css" />
  </head>
  <body>
    <main class="screen login-screen">
      <div class="login-panel">
        <p class="eyebrow">Sports Club Member</p>
        <h1>オンラインレッスン予約</h1>
        <form class="form-card" action="${pageContext.request.contextPath}/login" method="post">
          <label>
            メールアドレス
            <input name="email" type="email" placeholder="member@example.com" required />
          </label>
          <label>
            パスワード
            <input name="password" type="password" placeholder="password" required />
          </label>
          <p class="error">${error}</p>
          <button type="submit" class="primary full">ログイン</button>
        </form>
      </div>
    </main>
  </body>
</html>
