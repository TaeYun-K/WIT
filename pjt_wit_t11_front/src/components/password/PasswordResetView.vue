<template>
  <div class="min-h-screen flex flex-col">
    <!-- 네비게이션 바 -->
    <HeaderNavBar />

    <!-- 비밀번호 재설정 컨텐츠 -->
    <div class="flex-1 flex items-start justify-center pt-[130px] px-4 py-12">
      <div class="w-full max-w-md">
        <!-- 비밀번호 재설정 폼 -->
        <div class="bg-white p-8 rounded-lg shadow-md">
          <!-- 성공 상태 -->
          <div v-if="isResetSuccess" class="text-center">
            <div
              class="mx-auto flex items-center justify-center h-12 w-12 rounded-full bg-green-100 mb-4"
            >
              <svg
                class="h-6 w-6 text-green-600"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M5 13l4 4L19 7"
                ></path>
              </svg>
            </div>
            <h2 class="text-2xl font-bold text-gray-800 mb-2">비밀번호 변경 완료!</h2>
            <p class="text-gray-600 mb-6">새로운 비밀번호로 변경되었습니다.</p>
            <button
              @click="goToLogin"
              class="w-full bg-[#3c64b1] text-white py-3 px-4 rounded-md hover:bg-[#2a4580] transition duration-200 font-medium"
            >
              로그인하러 가기
            </button>
          </div>

          <!-- 폼 상태 -->
          <div v-else>
            <div class="text-center mb-8">
              <h2 class="text-3xl font-bold text-gray-800">새 비밀번호 설정</h2>
              <p class="mt-2 text-gray-600">새로운 비밀번호를 입력해주세요</p>
            </div>

            <!-- 에러 메시지 -->
            <div v-if="errorMessage" class="mb-6 p-4 bg-red-50 border border-red-200 rounded-md">
              <div class="flex">
                <svg class="w-5 h-5 text-red-400 mt-0.5" fill="currentColor" viewBox="0 0 20 20">
                  <path
                    fill-rule="evenodd"
                    d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z"
                    clip-rule="evenodd"
                  />
                </svg>
                <div class="ml-3">
                  <p class="text-sm text-red-700">{{ errorMessage }}</p>
                </div>
              </div>
            </div>

            <!-- 토큰 만료 메시지 -->
            <div
              v-if="isTokenExpired"
              class="mb-6 p-4 bg-yellow-50 border border-yellow-200 rounded-md"
            >
              <div class="flex">
                <svg class="w-5 h-5 text-yellow-400 mt-0.5" fill="currentColor" viewBox="0 0 20 20">
                  <path
                    fill-rule="evenodd"
                    d="M8.257 3.099c.765-1.36 2.722-1.36 3.486 0l5.58 9.92c.75 1.334-.213 2.98-1.742 2.98H4.42c-1.53 0-2.493-1.646-1.743-2.98l5.58-9.92zM11 13a1 1 0 11-2 0 1 1 0 012 0zm-1-8a1 1 0 00-1 1v3a1 1 0 002 0V6a1 1 0 00-1-1z"
                    clip-rule="evenodd"
                  />
                </svg>
                <div class="ml-3">
                  <h3 class="text-sm font-medium text-yellow-800">링크가 만료되었습니다</h3>
                  <p class="mt-1 text-sm text-yellow-700">
                    비밀번호 재설정 링크가 만료되었습니다. 새로운 링크를 요청해주세요.
                  </p>
                  <div class="mt-3">
                    <RouterLink
                      to="/password-reset-request"
                      class="text-sm font-medium text-yellow-800 hover:text-yellow-900 underline"
                    >
                      새 링크 요청하기 →
                    </RouterLink>
                  </div>
                </div>
              </div>
            </div>

            <form @submit.prevent="handlePasswordReset" class="space-y-6" v-if="!isTokenExpired">
              <!-- 새 비밀번호 입력 -->
              <div>
                <label for="password" class="block text-sm font-medium text-gray-700 mb-1">
                  새 비밀번호
                </label>
                <input
                  id="password"
                  v-model="password"
                  type="password"
                  required
                  class="w-full px-4 py-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-[#3c64b1] focus:border-transparent"
                  placeholder="새 비밀번호를 입력하세요 (8자 이상)"
                  autocomplete="new-password"
                  minlength="8"
                  :disabled="isLoading"
                />
                <p class="mt-1 text-xs text-gray-500">
                  8자 이상, 영문, 숫자, 특수문자를 포함해주세요
                </p>
              </div>

              <!-- 비밀번호 확인 -->
              <div>
                <label for="confirmPassword" class="block text-sm font-medium text-gray-700 mb-1">
                  비밀번호 확인
                </label>
                <input
                  id="confirmPassword"
                  v-model="confirmPassword"
                  type="password"
                  required
                  class="w-full px-4 py-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-[#3c64b1] focus:border-transparent"
                  placeholder="비밀번호를 다시 입력하세요"
                  autocomplete="new-password"
                  :disabled="isLoading"
                />
                <p
                  v-if="password && confirmPassword && password !== confirmPassword"
                  class="mt-1 text-xs text-red-500"
                >
                  비밀번호가 일치하지 않습니다
                </p>
              </div>

              <!-- 비밀번호 강도 표시 -->
              <div v-if="password" class="space-y-2">
                <div class="flex justify-between text-xs">
                  <span class="text-gray-600">비밀번호 강도</span>
                  <span :class="passwordStrengthColor">{{ passwordStrengthText }}</span>
                </div>
                <div class="w-full bg-gray-200 rounded-full h-2">
                  <div
                    class="h-2 rounded-full transition-all duration-300"
                    :class="passwordStrengthColor"
                    :style="{ width: passwordStrengthWidth }"
                  ></div>
                </div>
              </div>

              <!-- 재설정 버튼 -->
              <button
                type="submit"
                class="w-full bg-[#3c64b1] text-white py-3 px-4 rounded-md hover:bg-[#2a4580] transition duration-200 font-medium disabled:opacity-50 disabled:cursor-not-allowed"
                :disabled="isLoading || !isFormValid"
              >
                <span v-if="isLoading" class="flex items-center justify-center">
                  <svg
                    class="animate-spin -ml-1 mr-3 h-5 w-5 text-white"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                  >
                    <circle
                      class="opacity-25"
                      cx="12"
                      cy="12"
                      r="10"
                      stroke="currentColor"
                      stroke-width="4"
                    ></circle>
                    <path
                      class="opacity-75"
                      fill="currentColor"
                      d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
                    ></path>
                  </svg>
                  변경 중...
                </span>
                <span v-else>비밀번호 변경</span>
              </button>
            </form>
          </div>
        </div>

        <!-- 로그인 링크 -->
        <div class="text-center mt-6" v-if="!isResetSuccess">
          <p class="text-gray-600">
            비밀번호가 기억나셨나요?
            <RouterLink to="/login" class="text-[#3c64b1] font-medium hover:underline">
              로그인하기
            </RouterLink>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import axios from 'axios'
import HeaderNavBar from '@/components/HeaderNavBar.vue'

const router = useRouter()
const route = useRoute()

const password = ref('')
const confirmPassword = ref('')
const isLoading = ref(false)
const isResetSuccess = ref(false)
const isTokenExpired = ref(false)
const errorMessage = ref('')

// URL에서 토큰 추출
const resetToken = ref(route.query.token || '')

// 폼 유효성 검사
const isFormValid = computed(() => {
  return (
    password.value.length >= 8 &&
    password.value === confirmPassword.value &&
    passwordStrength.value >= 2 // 최소 보통 강도 이상
  )
})

// 비밀번호 강도 계산
const passwordStrength = computed(() => {
  const pwd = password.value
  let strength = 0

  if (pwd.length >= 8) strength++
  if (/[a-z]/.test(pwd)) strength++
  if (/[A-Z]/.test(pwd)) strength++
  if (/[0-9]/.test(pwd)) strength++
  if (/[^A-Za-z0-9]/.test(pwd)) strength++

  return strength
})

const passwordStrengthText = computed(() => {
  const strength = passwordStrength.value
  if (strength <= 1) return '매우 약함'
  if (strength === 2) return '약함'
  if (strength === 3) return '보통'
  if (strength === 4) return '강함'
  return '매우 강함'
})

const passwordStrengthColor = computed(() => {
  const strength = passwordStrength.value
  if (strength <= 1) return 'text-red-500 bg-red-500'
  if (strength === 2) return 'text-orange-500 bg-orange-500'
  if (strength === 3) return 'text-yellow-500 bg-yellow-500'
  if (strength === 4) return 'text-blue-500 bg-blue-500'
  return 'text-green-500 bg-green-500'
})

const passwordStrengthWidth = computed(() => {
  const strength = passwordStrength.value
  return `${(strength / 5) * 100}%`
})

// 컴포넌트 마운트 시 토큰 유효성 검사
onMounted(async () => {
  if (!resetToken.value) {
    isTokenExpired.value = true
    return
  }

  try {
    // 토큰 유효성 검사 API 호출
    await axios.get(`/api/auth/verify-reset-token?token=${resetToken.value}`)
  } catch (error) {
    console.error('토큰 검증 실패:', error)
    if (error.response?.status === 400 || error.response?.status === 404) {
      isTokenExpired.value = true
    } else {
      errorMessage.value = '토큰 검증 중 오류가 발생했습니다.'
    }
  }
})

const handlePasswordReset = async () => {
  if (!isFormValid.value) return

  isLoading.value = true
  errorMessage.value = ''

  try {
    const response = await axios.post('/api/auth/reset-password', {
      token: resetToken.value,
      newPassword: password.value,
    })

    console.log('비밀번호 재설정 성공', response.data)
    isResetSuccess.value = true
  } catch (error) {
    console.error('비밀번호 재설정 실패:', error)

    if (error.response?.status === 400) {
      isTokenExpired.value = true
    } else if (error.response?.status === 422) {
      errorMessage.value = '비밀번호 형식이 올바르지 않습니다.'
    } else {
      errorMessage.value =
        error.response?.data?.message || '비밀번호 변경 중 오류가 발생했습니다. 다시 시도해주세요.'
    }
  } finally {
    isLoading.value = false
  }
}

const goToLogin = () => {
  router.push('/login')
}
</script>
