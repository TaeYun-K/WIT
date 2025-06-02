<template>
  <div class="min-h-screen flex flex-col">
    <!-- 네비게이션 바 -->
    <HeaderNavBar />

    <!-- 회원가입 컨텐츠 -->
    <div class="flex-1 flex items-center justify-center px-4 py-12">
      <div class="w-full max-w-md">
        <!-- 회원가입 폼 -->
        <div class="bg-white p-8 rounded-lg shadow-md">
          <!-- 로고 및 타이틀 -->
          <div class="text-center mb-10">
            <h2 class="text-3xl font-bold text-gray-800">회원가입</h2>
            <p class="mt-2 text-gray-600">새로운 여행의 시작을 함께하세요</p>
          </div>
          <form @submit.prevent="handleSignup" class="space-y-5">
            <!-- 유저 이름 입력 -->
            <div>
              <label for="username" class="block text-sm font-medium text-gray-700 mb-1"
                >유저 이름</label
              >
              <input
                id="username"
                v-model="username"
                type="text"
                required
                class="w-full px-4 py-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-[#3c64b1] focus:border-transparent"
                placeholder="사용할 이름을 입력하세요"
              />
            </div>

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
              <label for="password" class="block text-sm font-medium text-gray-700 mb-1"
                >비밀번호</label
              >
              <input
                id="password"
                v-model="password"
                type="password"
                required
                class="w-full px-4 py-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-[#3c64b1] focus:border-transparent"
                placeholder="비밀번호를 입력하세요 (8자 이상)"
                autocomplete="new-password"
                minlength="8"
              />
              <p class="mt-1 text-xs text-gray-500">
                8자 이상, 영문, 숫자, 특수문자를 포함해주세요
              </p>
            </div>

            <!-- 비밀번호 확인 -->
            <div>
              <label for="confirmPassword" class="block text-sm font-medium text-gray-700 mb-1"
                >비밀번호 확인</label
              >
              <input
                id="confirmPassword"
                v-model="confirmPassword"
                type="password"
                required
                class="w-full px-4 py-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-[#3c64b1] focus:border-transparent"
                placeholder="비밀번호를 다시 입력하세요"
                autocomplete="new-password"
              />
              <p
                v-if="password && confirmPassword && password !== confirmPassword"
                class="mt-1 text-xs text-red-500"
              >
                비밀번호가 일치하지 않습니다
              </p>
            </div>

            <!-- 약관 동의 체크박스 -->
            <div class="space-y-3">
              <div class="flex items-start">
                <div class="flex items-center h-5">
                  <input
                    id="terms"
                    v-model="agreeTerms"
                    type="checkbox"
                    required
                    class="h-4 w-4 text-[#3c64b1] focus:ring-[#3c64b1] border-gray-300 rounded"
                  />
                </div>
                <div class="ml-3 text-sm">
                  <label for="terms" class="font-medium text-gray-700">이용약관에 동의합니다</label>
                  <p class="text-gray-500">
                    <a href="#" class="text-[#3c64b1] hover:underline">이용약관</a> 및
                    <a href="#" class="text-[#3c64b1] hover:underline">개인정보처리방침</a>을
                    읽었으며 이에 동의합니다.
                  </p>
                </div>
              </div>
            </div>

            <!-- 회원가입 버튼 -->
            <button
              type="submit"
              class="w-full bg-[#3c64b1] text-white py-3 px-4 rounded-md hover:bg-[#2a4580] transition duration-200 font-medium"
              :disabled="!isFormValid"
              :class="{ 'opacity-70 cursor-not-allowed': !isFormValid }"
            >
              회원가입
            </button>
          </form>
        </div>

        <!-- 로그인 링크 -->
        <div class="text-center mt-6">
          <p class="text-gray-600">
            이미 계정이 있으신가요?
            <RouterLink to="/login" class="text-[#3c64b1] font-medium hover:underline"
              >로그인</RouterLink
            >
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import HeaderNavBar from '@/components/HeaderNavBar.vue'

const router = useRouter()

const username = ref('')
const email = ref('')
const password = ref('')
const confirmPassword = ref('')
const agreeTerms = ref(false)

const isFormValid = computed(() => {
  return (
    username.value.trim() !== '' &&
    email.value.trim() !== '' &&
    password.value.length >= 8 &&
    password.value === confirmPassword.value &&
    agreeTerms.value
  )
})

const handleSignup = async () => {
  if (!isFormValid.value) return

  try {
    const response = await axios.post('http://localhost:8081/api/auth/signup', {
      username: username.value,
      password: password.value,
      email: email.value,
    })

    alert('회원가입이 완료되었습니다!')
    router.push('/Login')
  } catch (error) {
    alert(error.response?.data?.error || '회원가입 중 오류가 발생했습니다.')
  }
}
</script>
