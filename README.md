# オンラインレッスン予約システム

スポーツクラブ会員向けオンラインレッスン予約システムの研修用プロトタイプです。

## 構成

- Java Servlet / JSP
- Service層
- JDBC
- MySQL
- Maven
- Apache Tomcat 10系

### アプリケーション構成

```text
src/main/java/com/modoric/reservation/
├── controller/  Servlet。HTTPリクエストと画面遷移を制御
├── service/     検索・認証・予約の業務処理と例外変換
├── dao/         JDBCによるSQL実行とDBアクセス
├── model/       User、Lesson、Reservationのドメインモデル
└── util/        DB接続の共通処理

src/main/webapp/WEB-INF/views/  JSP。画面表示
```

呼び出しの流れは `JSP -> Servlet -> Service -> DAO -> MySQL` です。JSPからDAOを直接呼び出さず、ServletもServiceを介してDB操作を行います。

## 画面

- ログイン
- レッスン一覧、検索
- レッスン詳細
- 予約確認
- 予約完了

## DBセットアップ

MySQLで以下を実行します。

```bash
mysql -u root -p < database/schema.sql
mysql -u root -p < database/seed.sql
```

初期ログイン情報:

```text
メールアドレス: member@example.com
パスワード: password
```

## DB接続設定

デフォルトでは以下の接続先を使います。

```text
jdbc:mysql://localhost:3306/lesson_reservation
ユーザー: root
パスワード: 空文字
```

必要に応じて環境変数で上書きできます。

```bash
export DB_URL='jdbc:mysql://localhost:3306/lesson_reservation?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Tokyo'
export DB_USER='root'
export DB_PASSWORD='your_password'
```

## ビルド

Mavenをインストール後に実行します。

```bash
mvn clean package
```

生成されるWAR:

```text
target/lesson-reservation.war
```

このWARをTomcat 10系の `webapps` に配置すると動作します。

## Dockerで起動する場合

Docker Desktopを使える環境では、以下でMySQLとTomcatをまとめて起動できます。

```bash
docker compose up --build
```

起動後:

```text
http://localhost:8080/login
```

詳しくは [docs/local-setup.md](docs/local-setup.md) を参照してください。

## 補足

研修用のため、パスワードは平文で保存しています。実運用ではハッシュ化とHTTPSが必要です。

既存の静的プロトタイプは `index.html`, `styles.css`, `script.js` として残しています。
