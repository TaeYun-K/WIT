<template>
  <div class="min-h-screen flex flex-col">
    <!-- 네비게이션 바 -->
    <HeaderNavBar />

    <!-- 로그인 컨텐츠 -->
    <div class="flex-1 flex items-start justify-center pt-[130px] px-4 py-12">
      <div class="w-full max-w-md">
        <!-- 로고 및 타이틀 -->

        <!-- 로그인 폼 -->
        <div class="bg-white p-8 rounded-lg shadow-md">
          <div class="text-center mb-10">
            <h2 class="text-3xl font-bold text-gray-800">로그인</h2>
            <p class="mt-2 text-gray-600">여행의 새로운 경험을 시작하세요</p>
          </div>
          <form @submit.prevent="handleLogin" class="space-y-6">
            <!-- 이메일 입력 -->
            <div>
              <label for="email" class="block text-sm font-medium text-gray-700 mb-1">이메일</label>
              <input
                id="email"
                v-model="email"
                type="email"
                required
                class="w-full px-4 py-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-[#3c64b1] focus:border-transparent"
                placeholder="이메일 주소를 입력하세요"
                autocomplete="email"
              />
            </div>

            <!-- 비밀번호 입력 -->
            <div>
              <div class="flex items-center justify-between mb-1">
                <label for="password" class="block text-sm font-medium text-gray-700"
                  >비밀번호</label
                >
                <RouterLink
                  :to="{ name: 'passwordResetRequestView' }"
                  class="text-sm text-[#3c64b1] hover:underline"
                  >비밀번호 찾기</RouterLink
                >
              </div>
              <input
                id="password"
                v-model="password"
                type="password"
                required
                class="w-full px-4 py-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-[#3c64b1] focus:border-transparent"
                placeholder="비밀번호를 입력하세요"
                autocomplete="current-password"
                minlength="8"
              />
            </div>

            <!-- 로그인 유지 체크박스 -->
            <div class="flex items-center">
              <input
                id="remember-me"
                v-model="rememberMe"
                type="checkbox"
                class="h-4 w-4 text-[#3c64b1] focus:ring-[#3c64b1] border-gray-300 rounded"
              />
              <label for="remember-me" class="ml-2 block text-sm text-gray-700">로그인 유지</label>
            </div>

            <!-- 로그인 버튼 -->
            <button
              type="submit"
              class="w-full bg-[#3c64b1] text-white py-3 px-4 rounded-md hover:bg-[#2a4580] transition duration-200 font-medium"
            >
              로그인
            </button>
          </form>
        </div>

        <!-- 회원가입 링크 -->
        <div class="text-center mt-6">
          <p class="text-gray-600">
            아직 계정이 없으신가요?
            <RouterLink to="/signup" class="text-[#3c64b1] font-medium hover:underline"
              >회원가입</RouterLink
            >
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import HeaderNavBar from '@/components/HeaderNavBar.vue'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

// 사용자 입력 값
const email = ref('')
const password = ref('')
const rememberMe = ref(false)

const handleLogin = async () => {
  try {
    const response = await axios.post('/api/auth/login', {
      email: email.value, // 백엔드는 username으로 받음
      password: password.value,
    })
    console.log('로그인 성공', response.data)
    const token = response.data
    localStorage.setItem('jwt', token) // 로그인 토큰 저장

    userStore.token = token // Pinia 스토어에 토큰 저장
    await userStore.fetchUserInfo() // 사용자 정보 가져오기
    console.log('사용자 정보:', userStore.token)

    // 로그인 유지 기능
    if (rememberMe.value) {
      localStorage.setItem('email', email.value)
      console.log('이메일 저장:', email.value)
    } else {
      localStorage.removeItem('email')
    }

    // 로그인 성공 시 메인 페이지 이동
    router.push('/')
  } catch (error) {
    alert(error.response?.data || '로그인에 실패했습니다. 다시 시도해주세요.')
    console.error(error)
  }
}

onMounted(() => {
  // 페이지 로드 시 이메일 자동 입력
  const savedEmail = localStorage.getItem('email')
  if (savedEmail) {
    email.value = savedEmail
    rememberMe.value = true
  }
})
</script>
