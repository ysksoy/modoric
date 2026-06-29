const lessons = [
  { id: 1, category: "ヨガ", date: "2026-06-26", time: "09:00-09:45", title: "朝のリフレッシュヨガ", instructor: "佐藤 美咲", level: "初級", capacity: 18 },
  { id: 2, category: "筋力トレーニング", date: "2026-06-26", time: "10:30-11:15", title: "自重で始める体幹トレーニング", instructor: "田中 健", level: "初級", capacity: 20 },
  { id: 3, category: "ダンス", date: "2026-06-26", time: "12:00-12:45", title: "はじめてのラテンステップ", instructor: "山本 彩", level: "初級", capacity: 16 },
  { id: 4, category: "ピラティス", date: "2026-06-26", time: "19:00-19:45", title: "姿勢改善ピラティス", instructor: "鈴木 翔子", level: "中級", capacity: 14 },
  { id: 5, category: "ストレッチ", date: "2026-06-27", time: "08:30-09:00", title: "肩こり解消ストレッチ", instructor: "高橋 亮", level: "初級", capacity: 25 },
  { id: 6, category: "ヨガ", date: "2026-06-27", time: "10:00-10:45", title: "呼吸を整えるベーシックヨガ", instructor: "佐藤 美咲", level: "初級", capacity: 18 },
  { id: 7, category: "有酸素", date: "2026-06-27", time: "11:30-12:15", title: "脂肪燃焼エアロ", instructor: "中村 優", level: "中級", capacity: 22 },
  { id: 8, category: "筋力トレーニング", date: "2026-06-27", time: "14:00-14:45", title: "下半身集中ワークアウト", instructor: "田中 健", level: "中級", capacity: 20 },
  { id: 9, category: "ピラティス", date: "2026-06-27", time: "18:00-18:45", title: "コアを鍛えるピラティス", instructor: "鈴木 翔子", level: "中級", capacity: 14 },
  { id: 10, category: "ダンス", date: "2026-06-28", time: "09:30-10:15", title: "K-POPダンス入門", instructor: "山本 彩", level: "初級", capacity: 16 },
  { id: 11, category: "ストレッチ", date: "2026-06-28", time: "11:00-11:30", title: "全身リセットストレッチ", instructor: "高橋 亮", level: "初級", capacity: 25 },
  { id: 12, category: "ヨガ", date: "2026-06-28", time: "13:00-13:45", title: "リラックスフローヨガ", instructor: "佐藤 美咲", level: "初級", capacity: 18 },
  { id: 13, category: "有酸素", date: "2026-06-28", time: "15:00-15:45", title: "ステップエクササイズ", instructor: "中村 優", level: "中級", capacity: 22 },
  { id: 14, category: "筋力トレーニング", date: "2026-06-29", time: "07:30-08:15", title: "朝活サーキット", instructor: "田中 健", level: "中級", capacity: 20 },
  { id: 15, category: "ピラティス", date: "2026-06-29", time: "12:30-13:15", title: "ランチタイムピラティス", instructor: "鈴木 翔子", level: "初級", capacity: 14 },
  { id: 16, category: "ヨガ", date: "2026-06-29", time: "20:00-20:45", title: "夜のメンテナンスヨガ", instructor: "佐藤 美咲", level: "初級", capacity: 18 },
  { id: 17, category: "ダンス", date: "2026-06-30", time: "10:00-10:45", title: "ジャズダンス基礎", instructor: "山本 彩", level: "中級", capacity: 16 },
  { id: 18, category: "ストレッチ", date: "2026-06-30", time: "13:00-13:30", title: "腰まわり集中ストレッチ", instructor: "高橋 亮", level: "初級", capacity: 25 },
  { id: 19, category: "有酸素", date: "2026-06-30", time: "18:30-19:15", title: "ボクササイズ", instructor: "中村 優", level: "中級", capacity: 22 },
  { id: 20, category: "筋力トレーニング", date: "2026-07-01", time: "09:00-09:45", title: "ダンベル基礎", instructor: "田中 健", level: "初級", capacity: 20 },
  { id: 21, category: "ヨガ", date: "2026-07-01", time: "11:00-11:45", title: "バランスヨガ", instructor: "佐藤 美咲", level: "中級", capacity: 18 },
  { id: 22, category: "ピラティス", date: "2026-07-01", time: "19:30-20:15", title: "背中すっきりピラティス", instructor: "鈴木 翔子", level: "初級", capacity: 14 }
];

let selectedLesson = null;
const reservedLessonIds = new Set();

const screens = [...document.querySelectorAll("[data-screen]")];
const lessonList = document.querySelector("#lessonList");
const lessonCount = document.querySelector("#lessonCount");
const lessonListTitle = document.querySelector("#lessonListTitle");
const searchMessage = document.querySelector("#searchMessage");

function showScreen(name) {
  screens.forEach((screen) => {
    screen.classList.toggle("hidden", screen.dataset.screen !== name);
  });
  window.scrollTo({ top: 0, behavior: "auto" });
}

function uniqueValues(key) {
  return [...new Set(lessons.map((lesson) => lesson[key]))].sort();
}

function fillSelect(id, values) {
  const select = document.querySelector(id);
  values.forEach((value) => {
    const option = document.createElement("option");
    option.value = value;
    option.textContent = value;
    select.appendChild(option);
  });
}

function formatDate(value) {
  return new Intl.DateTimeFormat("ja-JP", {
    year: "numeric",
    month: "long",
    day: "numeric",
    weekday: "short"
  }).format(new Date(`${value}T00:00:00`));
}

function sortLessonsBySchedule(items) {
  return [...items].sort((a, b) => {
    const dateComparison = a.date.localeCompare(b.date);
    return dateComparison !== 0 ? dateComparison : a.time.localeCompare(b.time);
  });
}

function recentLessons() {
  return sortLessonsBySchedule(lessons).slice(0, 20);
}

function renderLessons(items, title) {
  lessonListTitle.textContent = title;
  lessonCount.textContent = `${items.length}件`;
  lessonList.innerHTML = "";

  if (items.length === 0) {
    lessonList.innerHTML = '<p class="message">該当するレッスンはありません。</p>';
    return;
  }

  sortLessonsBySchedule(items).forEach((lesson) => {
    const card = document.createElement("button");
    card.className = "lesson-card";
    card.type = "button";
    card.innerHTML = `
      <span class="badge">${lesson.category}</span>
      <h3>${lesson.title}</h3>
      <div class="lesson-meta">
        <span>${formatDate(lesson.date)} ${lesson.time}</span>
        <span>講師：${lesson.instructor}</span>
        <span>レベル：${lesson.level} / 定員：${lesson.capacity}名</span>
      </div>
    `;
    card.addEventListener("click", () => openDetail(lesson.id));
    lessonList.appendChild(card);
  });
}

function lessonInfoBoxes(lesson, includeStreaming = false) {
  const boxes = [
    ["日時", formatDate(lesson.date)],
    ["時間帯", lesson.time],
    ["レッスン名", lesson.title],
    ["インストラクター", lesson.instructor]
  ];

  if (includeStreaming) {
    boxes.push(["ストリーミングID", `LIVE-${String(lesson.id).padStart(4, "0")}`]);
    boxes.push(["ストリーミングパスワード", `pass${lesson.date.replaceAll("-", "")}`]);
  }

  return boxes
    .map(([label, value]) => `<div class="info-box"><span>${label}</span><strong>${value}</strong></div>`)
    .join("");
}

function openDetail(id) {
  selectedLesson = lessons.find((lesson) => lesson.id === id);
  document.querySelector("#detailTitle").textContent = selectedLesson.title;
  document.querySelector("#detailContent").innerHTML = `
    <p id="detailError" class="message"></p>
    <div class="detail-summary">${lessonInfoBoxes(selectedLesson)}</div>
    <div class="info-box">
      <span>レッスン概要</span>
      <strong>${selectedLesson.category}の${selectedLesson.level}クラスです。オンライン参加用のため、自宅で動けるスペースと飲み物を準備してください。</strong>
    </div>
    <div class="info-box">
      <span>定員</span>
      <strong>${selectedLesson.capacity}名</strong>
    </div>
  `;
  showScreen("detail");
}

function openConfirm() {
  if (reservedLessonIds.has(selectedLesson.id)) {
    document.querySelector("#detailError").textContent = "このレッスンはすでに予約済みのため、予約できません。";
    return;
  }

  document.querySelector("#confirmContent").innerHTML = `
    <h2>以下の内容で予約します</h2>
    <div class="reservation-list">${lessonInfoBoxes(selectedLesson)}</div>
  `;
  showScreen("confirm");
}

function openComplete() {
  reservedLessonIds.add(selectedLesson.id);
  document.querySelector("#completeContent").innerHTML = `
    <h2>オンライン参加情報</h2>
    <div class="reservation-list">${lessonInfoBoxes(selectedLesson, true)}</div>
  `;
  showScreen("complete");
}

function searchLessons() {
  const category = document.querySelector("#categoryFilter").value;
  const date = document.querySelector("#dateFilter").value;
  const instructor = document.querySelector("#instructorFilter").value;

  if (!category && !date && !instructor) {
    searchMessage.textContent = "カテゴリー、日時、インストラクターのいずれかを選択してください。";
    return;
  }

  searchMessage.textContent = "";
  const results = lessons.filter((lesson) => {
    return (
      (!category || lesson.category === category) &&
      (!date || lesson.date === date) &&
      (!instructor || lesson.instructor === instructor)
    );
  });
  renderLessons(results, "検索結果");
}

function resetLessonSearch() {
  document.querySelector("#categoryFilter").value = "";
  document.querySelector("#dateFilter").value = "";
  document.querySelector("#instructorFilter").value = "";
  searchMessage.textContent = "";
}

document.querySelector("#loginForm").addEventListener("submit", (event) => {
  event.preventDefault();
  const email = document.querySelector("#email").value.trim();
  const password = document.querySelector("#password").value.trim();

  if (!email || !password) {
    document.querySelector("#loginError").textContent = "メールアドレスとパスワードを入力してください。";
    return;
  }

  document.querySelector("#loginError").textContent = "";
  showScreen("home");
});

document.querySelector("#logoutButton").addEventListener("click", () => showScreen("login"));
document.querySelector("#searchButton").addEventListener("click", searchLessons);
document.querySelector("#detailBackButton").addEventListener("click", () => showScreen("home"));
document.querySelector("#reserveButton").addEventListener("click", openConfirm);
document.querySelector("#confirmBackButton").addEventListener("click", () => showScreen("detail"));
document.querySelector("#completeButton").addEventListener("click", openComplete);
document.querySelector("#homeButton").addEventListener("click", () => {
  resetLessonSearch();
  renderLessons(recentLessons(), "直近のレッスン");
  showScreen("home");
});

fillSelect("#categoryFilter", uniqueValues("category"));
fillSelect("#instructorFilter", uniqueValues("instructor"));
renderLessons(recentLessons(), "直近のレッスン");
