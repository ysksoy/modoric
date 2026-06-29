# ローカル起動手順

## Dockerで起動する場合

Docker Desktopをインストール済みなら、プロジェクトルートで実行します。

```bash
docker compose up --build
```

起動後にブラウザで開きます。

```text
http://localhost:8080/login
```

ログイン情報:

```text
メールアドレス: member@example.com
パスワード: password
```

停止:

```bash
docker compose down
```

DBデータも消して初期化し直す場合:

```bash
docker compose down -v
```

## 手動で起動する場合

必要なもの:

- JDK 17以上
- Maven
- MySQL 8系
- Tomcat 10.1系

DB作成:

```bash
mysql -u root -p < database/schema.sql
mysql -u root -p < database/seed.sql
```

WAR作成:

```bash
mvn clean package
```

生成された `target/lesson-reservation.war` をTomcatの `webapps` に配置します。

## DB接続の変更

アプリは以下の環境変数を読みます。

```bash
export DB_URL='jdbc:mysql://localhost:3306/lesson_reservation?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Tokyo'
export DB_USER='root'
export DB_PASSWORD='your_password'
```
