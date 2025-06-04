<!-- src/App.vue -->
<template>
  <div class="app-container">
    <!-- 히어로 섹션 -->
    <section class="hero">
      <!-- 왼쪽 텍스트 -->
      <div class="hero-text">
        <h1>"We Innovate Trip."</h1>
        <p>
          지금 당신의 여행을 더 새롭고, 더 함께하게 만드세요.<br />
          추천, 계획, 공유까지 — 모두 한 곳에서.
        </p>
        <button class="cta-btn" @click="showModal = true">계획 세우기</button>
        <Modal v-model="showModal" @submit="onCreate" />
      </div>

      <!-- 오른쪽 이미지 박스 -->
      <div class="hero-graphic">
        <video src="/intro.mp4" autoplay muted loop playsinline class="hero-video"></video>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import Modal from '@/components/PlanModal.vue'
import { useRouter } from 'vue-router'
const showModal = ref(false)
const router = useRouter()

function onCreate({ name, days, startDate }) {
  console.log('새 일정:', name, days, '일', startDate ? `(${startDate} 시작)` : '')
  router.push('/')
  // 라우터 이동이나 API 호출...
  router.push({
    name: 'LS', // LocationSelection 이 마운트되는 라우트 이름
    query: {
      tripName: name,
      tripDays: days,
      tripStart: startDate,
    },
  })
}
</script>

<style scoped>
/* 화면 전체 컨테이너 (1920×1080 기준) */
.app-container {
  max-width: 1920px;
  height: 800px;
  margin: 0 auto;
  font-family: 'Noto Sans KR', sans-serif;
  color: #333;
}

.hero-video {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 또는 contain */
}

/* 히어로 섹션 (높이는 네비게이션 제외한 전체) */
.hero {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 2rem 9rem;
  height: calc(100% - 4rem); /* nav 높이 대략 4rem */
}
.hero-text {
  max-width: 40%;
}
.hero-text h1 {
  font-size: 3rem;
  margin-bottom: 1rem;
}
.hero-text p {
  font-size: 1.125rem;
  line-height: 1.6;
  margin-bottom: 2rem;
}
.cta-btn {
  padding: 0.75rem 1.5rem;
  background-color: #3c64b1;
  color: #fff;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
}

/* 오른쪽 비주얼 박스 (960×540) */
.hero-graphic {
  width: 800px;
  height: 450px;
  background-color: #eef2f6;
  border: 1px solid #3661c7;
  border-radius: 4px;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
}
.hero-graphic img {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
}
</style>
