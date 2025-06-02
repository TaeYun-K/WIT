<template>
  <nav class="nav">
    <div class="logo">
      <img :src="logo" alt="WIT Logo" @click="goHome" class="cursor-pointer" />
    </div>
    <ul class="nav-links">
      <li>
        <RouterLink :to="{ name: 'home' }">홈</RouterLink>
      </li>
      <li>
        <RouterLink :to="{ name: 'LS' }">소개</RouterLink>
      </li>
      <li>
        <RouterLink :to="{ name: 'Board' }">커뮤니티</RouterLink>
      </li>
      <li><a href="/discover">Discover</a></li>
      <li><a href="/feedback">Feedback</a></li>
    </ul>
    <div class="auth-buttons flex items-center space-x-4">
      <template v-if="userStore.token">
        <!-- 프로필 아이콘 드롭다운 -->
        <div class="relative">
          <button
            @click="showUserDropdown = !showUserDropdown"
            class="profile-btn flex items-center justify-center w-10 h-10 bg-white rounded-full shadow-md hover:shadow-lg transition-shadow duration-300"
            title="사용자 메뉴"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="h-6 w-6 text-blue-600"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
              />
            </svg>
          </button>

          <!-- 드롭다운 메뉴 -->
          <div
            v-if="showUserDropdown"
            class="absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-lg border border-gray-200 py-2 z-40"
            @click.stop
          >
            <button
              @click="goToMyPage"
              class="w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-blue-50 hover:text-blue-600 transition-colors flex items-center"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-4 w-4 mr-3"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
                />
              </svg>
              마이페이지
            </button>
            <button
              @click="logout"
              class="w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-red-50 hover:text-red-600 transition-colors flex items-center"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-4 w-4 mr-3"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"
                />
              </svg>
              로그아웃
            </button>
          </div>
        </div>
      </template>
      <template v-else>
        <RouterLink :to="{ name: 'login' }" class="login-btn">로그인</RouterLink>
      </template>
    </div>
  </nav>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import logoImg from '@/assets/logo.png'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const logo = ref(logoImg)
const router = useRouter()
const showUserDropdown = ref(false)
const userStore = useUserStore()

console.log('User Store:', userStore)
// 사용자 드롭다운 토글 상태

// 드롭다운 외부 클릭 감지
const handleClickOutside = (event) => {
  if (showUserDropdown.value && !event.target.closest('.relative')) {
    showUserDropdown.value = false
  }
}

onMounted(() => {
  // 외부 클릭 이벤트 리스너 추가
  document.addEventListener('click', handleClickOutside)
})

onUnmounted(() => {
  // 컴포넌트 언마운트 시 이벤트 리스너 제거
  document.removeEventListener('click', handleClickOutside)
})

const logout = () => {
  userStore.logout() // 사용자 스토어에서 로그아웃 처리
  console.log('로그아웃 처리됨' + userStore.token)
  showUserDropdown.value = false // 드롭다운 닫기
  router.push('/login') // 로그인 페이지로 이동
}

const goToMyPage = () => {
  showUserDropdown.value = false // 드롭다운 닫기
  router.push({ name: 'mypage' })
}

const goHome = () => {
  // 홈으로 이동하는 함수
  router.push({ name: 'home' })
}
</script>

<style scoped>
/* 네비게이션 (기존과 동일) */
.nav {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1rem 2rem;
}
.logo {
  display: flex;
  align-items: center;
}
.logo img {
  width: 110px;
  margin-right: 0.5rem;
}
.nav-links {
  display: flex;
  gap: 5rem;
  list-style: none;
}

.nav-links a {
  text-decoration: none;
  color: #333;
  font-weight: 500;
  font-size: 18px;
  transition: color 0.2s ease; /* 색 변화 부드럽게 */
}

/* li에 hover 시 자식 <a>의 색만 변경 */
.nav-links li:hover a {
  color: #3c64b1; /* 원하는 하이라이트 컬러로 변경 */
}

.auth-buttons {
  display: flex;
  align-items: center;
  /* 로그인 버튼 기준으로 넉넉히 고정 너비 지정 */
  width: 6rem; /* 필요에 따라 조정하세요 */
  justify-content: flex-end;
}
.profile-btn {
  margin-left: -5rem; /* 원하는 만큼 당겨보세요 */
}

/* 로그인 버튼 기본 스타일 */
.login-btn {
  padding: 0.5rem 1rem;
  font-size: 18px;
  font-weight: 600;
  color: #333; /* 기본 텍스트 컬러 (진한 그레이) */
  background: transparent; /* 배경 없앰 */
  border: none; /* 경계선 없음 */
  border-radius: 6px; /* 모서리 살짝 둥글게 */
  cursor: pointer;
  position: relative;
  left: -2rem;
  transition:
    background-color 0.2s ease,
    color 0.2s ease,
    transform 0.2s ease;
}

/* 호버 시 스타일 */
.login-btn:hover {
  background-color: #3c64b1; /* 검정 배경 */
  color: #ffffff; /* 흰 텍스트 */
  transform: translateY(-1px);
}
</style>
