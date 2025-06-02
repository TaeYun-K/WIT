<template>
  <div class="min-h-screen flex flex-col">
    <!-- 네비게이션 바 -->
    <HeaderNavBar />

    <!-- 비밀번호 찾기 컨텐츠 -->
    <div class="flex-1 flex items-start justify-center pt-[130px] px-4 py-12">
      <div class="w-full max-w-md">
        <!-- 비밀번호 찾기 폼 -->
        <div class="bg-white p-8 rounded-lg shadow-md">
          <div class="text-center mb-8">
            <h2 class="text-3xl font-bold text-gray-800">비밀번호 찾기</h2>
            <p class="mt-2 text-gray-600">가입하신 이메일 주소를 입력해주세요</p>
            <p class="mt-1 text-sm text-gray-500">비밀번호 재설정 링크를 보내드립니다</p>
          </div>

          <!-- 성공 메시지 -->
          <div v-if="isEmailSent" class="mb-6 p-4 bg-green-50 border border-green-200 rounded-md">
            <div class="flex">
              <svg class="w-5 h-5 text-green-400 mt-0.5" fill="currentColor" viewBox="0 0 20 20">
                <path
                  fill-rule="evenodd"
                  d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
                  clip-rule="evenodd"
                />
              </svg>
              <div class="ml-3">
                <h3 class="text-sm font-medium text-green-800">이메일이 전송되었습니다!</h3>
                <p class="mt-1 text-sm text-green-700">
                  {{ email }}로 비밀번호 재설정 링크를 보내드렸습니다.<br />
                  이메일을 확인해주세요.
                </p>
              </div>
            </div>
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

          <form @submit.prevent="handlePasswordReset" class="space-y-6" v-if="!isEmailSent">
            <!-- 이메일 입력 -->
            <div>
              <label for="email" class="block text-sm font-medium text-gray-700 mb-1">이메일</label>
              <input
                id="email"
                v-model="email"
                type="email"
                required
                class="w-full px-4 py-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-[#3c64b1] focus:border-transparent"
                placeholder="가입하신 이메일 주소를 입력하세요"
                autocomplete="email"
                :disabled="isLoading"
              />
            </div>

            <!-- 전송 버튼 -->
            <button
              type="submit"
              class="w-full bg-[#3c64b1] text-white py-3 px-4 rounded-md hover:bg-[#2a4580] transition duration-200 font-medium disabled:opacity-50 disabled:cursor-not-allowed"
              :disabled="isLoading || !email.trim()"
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
                전송 중...
              </span>
              <span v-else>비밀번호 재설정 링크 전송</span>
            </button>
          </form>

          <!-- 재전송 버튼 (이메일 전송 후) -->
          <div v-if="isEmailSent" class="space-y-4">
            <button
              @click="resendEmail"
              class="w-full bg-gray-600 text-white py-3 px-4 rounded-md hover:bg-gray-700 transition duration-200 font-medium disabled:opacity-50 disabled:cursor-not-allowed"
              :disabled="isLoading || resendCooldown > 0"
            >
              <span v-if="resendCooldown > 0"> {{ resendCooldown }}초 후 재전송 가능 </span>
              <span v-else-if="isLoading">재전송 중...</span>
              <span v-else>이메일 재전송</span>
            </button>
          </div>
        </div>

        <!-- 로그인 링크 -->
        <div class="text-center mt-6">
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
import { ref, onMounted } from 'vue'
import axios from 'axios'
import HeaderNavBar from '@/components/HeaderNavBar.vue'

const email = ref('')
const isLoading = ref(false)
const isEmailSent = ref(false)
const errorMessage = ref('')
const resendCooldown = ref(0)

const startResendCooldown = () => {
  resendCooldown.value = 60 // 60초 쿨다운
  const interval = setInterval(() => {
    resendCooldown.value--
    if (resendCooldown.value <= 0) {
      clearInterval(interval)
    }
  }, 1000)
}

onMounted(() => {
  // Ensure startResendCooldown is called only when the component is mounted
})

const handlePasswordReset = async () => {
  if (!email.value.trim()) return

  isLoading.value = true
  errorMessage.value = ''

  try {
    const response = await axios.post('/api/auth/password-reset-request', {
      email: email.value,
    })

    console.log('비밀번호 재설정 요청 성공', response.data)
    isEmailSent.value = true
    startResendCooldown()
  } catch (error) {
    console.error('비밀번호 재설정 요청 실패:', error)

    if (error.response?.status === 404) {
      errorMessage.value = '등록되지 않은 이메일 주소입니다.'
    } else if (error.response?.status === 429) {
      errorMessage.value = '너무 많은 요청이 발생했습니다. 잠시 후 다시 시도해주세요.'
    } else {
      errorMessage.value =
        error.response?.data?.message || '요청 처리 중 오류가 발생했습니다. 다시 시도해주세요.'
    }
  } finally {
    isLoading.value = false
  }
}

const resendEmail = async () => {
  if (resendCooldown.value > 0) return

  isLoading.value = true
  errorMessage.value = ''

  try {
    await axios.post('/api/auth/password-reset-request', {
      email: email.value,
    })

    startResendCooldown()
    errorMessage.value = ''
  } catch (error) {
    console.error('이메일 재전송 실패:', error)
    errorMessage.value = '이메일 재전송에 실패했습니다. 다시 시도해주세요.'
  } finally {
    isLoading.value = false
  }
}
</script>
