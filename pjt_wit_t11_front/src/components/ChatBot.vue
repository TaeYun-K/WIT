<template>
  <div class="fixed bottom-6 right-6 z-50">
    <!-- 챗봇 아이콘 -->
    <button
      @click="toggleChat"
      class="group relative bg-gradient-to-r from-blue-600 to-blue-700 hover:from-blue-700 hover:to-blue-800 text-white p-4 rounded-full shadow-lg hover:shadow-xl transform hover:scale-105 transition-all duration-300"
    >
      <!-- 채팅 아이콘 -->
      <svg
        xmlns="http://www.w3.org/2000/svg"
        class="h-6 w-6"
        fill="none"
        viewBox="0 0 24 24"
        stroke="currentColor"
      >
        <path
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="2"
          d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z"
        />
      </svg>

      <!-- 알림 점 (새 메시지가 있을 때) -->
      <div
        v-if="hasNewMessage"
        class="absolute -top-1 -right-1 w-3 h-3 bg-red-500 rounded-full animate-pulse"
      ></div>

      <!-- 툴팁 -->
      <div
        class="absolute bottom-full right-0 mb-2 px-3 py-1 bg-gray-800 text-white text-sm rounded-lg opacity-0 group-hover:opacity-100 transition-opacity duration-300 whitespace-nowrap"
      >
        여행 AI 도우미
        <div
          class="absolute top-full right-4 w-0 h-0 border-l-4 border-r-4 border-t-4 border-transparent border-t-gray-800"
        ></div>
      </div>
    </button>

    <!-- 채팅창 -->
    <transition
      enter-active-class="transition ease-out duration-300"
      enter-from-class="transform opacity-0 scale-95 translate-y-4"
      enter-to-class="transform opacity-100 scale-100 translate-y-0"
      leave-active-class="transition ease-in duration-200"
      leave-from-class="transform opacity-100 scale-100 translate-y-0"
      leave-to-class="transform opacity-0 scale-95 translate-y-4"
    >
      <div
        v-if="showChat"
        class="absolute bottom-16 right-0 w-[500px] h-[600px] bg-white shadow-2xl rounded-2xl overflow-hidden border border-gray-200"
      >
        <!-- 헤더 -->
        <div
          class="bg-gradient-to-r from-blue-600 to-blue-700 text-white p-4 flex items-center justify-between"
        >
          <div class="flex items-center space-x-3">
            <div
              class="w-8 h-8 bg-white bg-opacity-20 rounded-full flex items-center justify-center"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-5 w-5"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z"
                />
              </svg>
            </div>
            <div>
              <h3 class="font-semibold text-sm">WIT 여행 도우미</h3>
              <p class="text-xs opacity-90">여행 계획을 도와드려요!</p>
            </div>
          </div>
          <button
            @click="toggleChat"
            class="text-white hover:bg-white hover:bg-opacity-20 rounded-full p-1 transition-colors duration-200"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="h-5 w-5"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M6 18L18 6M6 6l12 12"
              />
            </svg>
          </button>
        </div>

        <!-- 메시지 영역 -->
        <div
          ref="messagesContainer"
          class="flex-1 overflow-y-auto p-4 space-y-3 h-[450px] bg-gray-50"
        >
          <!-- 초기 환영 메시지 -->
          <div v-if="messages.length === 0" class="text-center text-gray-500 text-sm py-8">
            <div
              class="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-3"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-8 w-8 text-blue-600"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M9 20l-5.447-2.724A1 1 0 013 16.382V5.618a1 1 0 011.447-.894L9 7m0 13l6-3m-6 3V7m6 10l4.553 2.276A1 1 0 0021 18.382V7.618a1 1 0 00-.553-.894L15 4m0 13V4m0 0L9 7"
                />
              </svg>
            </div>
            <p class="font-medium mb-1">안녕하세요! 👋</p>
            <p class="text-xs">
              여행지, 기간, 성향을 알려주시면<br />맞춤 여행 계획을 추천해드려요!
            </p>
            <p class="text-xs mt-2 text-blue-600">예) 제주도 / 3박4일 / ISTP</p>
          </div>

          <!-- 메시지 목록 -->
          <div
            v-for="(msg, index) in messages"
            :key="index"
            class="flex"
            :class="msg.role === 'user' ? 'justify-end' : 'justify-start'"
          >
            <div class="max-w-xs">
              <!-- 사용자 메시지 -->
              <div
                v-if="msg.role === 'user'"
                class="bg-blue-600 text-white px-4 py-2 rounded-2xl rounded-br-md shadow-sm"
              >
                <p class="text-sm">{{ msg.content }}</p>
              </div>

              <!-- AI 메시지 -->
              <div v-else class="flex items-start space-x-2">
                <div
                  class="w-6 h-6 bg-gray-300 rounded-full flex items-center justify-center flex-shrink-0 mt-1"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    class="h-3 w-3 text-gray-600"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z"
                    />
                  </svg>
                </div>
                <div
                  class="bg-white border border-gray-200 px-4 py-2 rounded-2xl rounded-bl-md shadow-sm"
                >
                  <p class="text-sm text-gray-800 whitespace-pre-line">{{ msg.content }}</p>
                </div>
              </div>
            </div>
          </div>

          <!-- 로딩 인디케이터 -->
          <div v-if="isLoading" class="flex justify-start">
            <div class="flex items-start space-x-2">
              <div
                class="w-6 h-6 bg-gray-300 rounded-full flex items-center justify-center flex-shrink-0 mt-1"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="h-3 w-3 text-gray-600"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z"
                  />
                </svg>
              </div>
              <div
                class="bg-white border border-gray-200 px-4 py-2 rounded-2xl rounded-bl-md shadow-sm"
              >
                <div class="flex space-x-1">
                  <div class="w-2 h-2 bg-gray-400 rounded-full animate-bounce"></div>
                  <div
                    class="w-2 h-2 bg-gray-400 rounded-full animate-bounce"
                    style="animation-delay: 0.1s"
                  ></div>
                  <div
                    class="w-2 h-2 bg-gray-400 rounded-full animate-bounce"
                    style="animation-delay: 0.2s"
                  ></div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 입력 영역 -->
        <div class="p-4 border-t border-gray-200 bg-white">
          <div class="flex space-x-2">
            <input
              v-model="input"
              @keyup.enter="sendMessage"
              :disabled="isLoading"
              class="flex-1 border border-gray-300 rounded-full px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent disabled:bg-gray-100 disabled:cursor-not-allowed"
              placeholder="여행지, 기간, 취향을 알려주세요..."
            />
            <button
              @click="sendMessage"
              :disabled="isLoading || !input.trim()"
              class="bg-blue-600 hover:bg-blue-700 disabled:bg-gray-300 disabled:cursor-not-allowed text-white px-4 py-2 rounded-full transition-colors duration-200 flex items-center justify-center"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-4 w-4"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8"
                />
              </svg>
            </button>
          </div>

          <!-- 빠른 응답 버튼들 -->
          <div v-if="messages.length === 0" class="flex flex-wrap gap-2 mt-3">
            <button
              v-for="suggestion in quickSuggestions"
              :key="suggestion"
              @click="sendQuickMessage(suggestion)"
              class="text-xs bg-gray-100 hover:bg-gray-200 text-gray-700 px-3 py-1 rounded-full transition-colors duration-200"
            >
              {{ suggestion }}
            </button>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { ref, nextTick, onMounted } from 'vue'
import axios from 'axios'

const showChat = ref(false)
const input = ref('')
const messages = ref([])
const isLoading = ref(false)
const hasNewMessage = ref(false)
const messagesContainer = ref(null)

const toggleChat = () => {
  showChat.value = !showChat.value
  if (showChat.value) {
    hasNewMessage.value = false
  }
}

const scrollToBottom = () => {
  nextTick(() => {
    if (messagesContainer.value) {
      messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
    }
  })
}

const sendMessage = async () => {
  if (!input.value.trim() || isLoading.value) return

  const userMessage = input.value.trim()
  messages.value.push({ role: 'user', content: userMessage })
  input.value = ''
  isLoading.value = true

  scrollToBottom()

  try {
    const response = await axios.post('/api/chat', { message: userMessage })
    messages.value.push({ role: 'assistant', content: response.data.answer })
  } catch (err) {
    console.error('Chat error:', err)
    messages.value.push({
      role: 'assistant',
      content: '죄송합니다. 일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.',
    })
  } finally {
    isLoading.value = false
    scrollToBottom()

    // 채팅창이 닫혀있으면 새 메시지 알림 표시
    if (!showChat.value) {
      hasNewMessage.value = true
    }
  }
}

const sendQuickMessage = (suggestion) => {
  input.value = suggestion
  sendMessage()
}

onMounted(() => {
  // 컴포넌트 마운트 시 초기화 작업
})
</script>

<style scoped>
/* 스크롤바 스타일링 */
.overflow-y-auto::-webkit-scrollbar {
  width: 4px;
}

.overflow-y-auto::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 2px;
}

.overflow-y-auto::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 2px;
}

.overflow-y-auto::-webkit-scrollbar-thumb:hover {
  background: #a8a8a8;
}

/* 애니메이션 */
@keyframes bounce {
  0%,
  80%,
  100% {
    transform: scale(0);
  }
  40% {
    transform: scale(1);
  }
}

.animate-bounce {
  animation: bounce 1.4s infinite ease-in-out both;
}
</style>
