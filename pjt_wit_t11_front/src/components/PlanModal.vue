<template>
  <teleport to="body">
    <transition name="modal-fade">
      <div v-if="visible" class="modal-overlay" @click.self="close">
        <div class="modal-container">
          <!-- 헤더 -->
          <div class="modal-header">
            <h2 class="modal-title">새로운 여행 계획</h2>
            <button @click="close" class="modal-close-btn">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="24"
                height="24"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
                class="w-5 h-5"
              >
                <line x1="18" y1="6" x2="6" y2="18"></line>
                <line x1="6" y1="6" x2="18" y2="18"></line>
              </svg>
            </button>
          </div>

          <!-- 일러스트레이션 -->
          <div class="modal-illustration">
            <div class="illustration-circle">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="48"
                height="48"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                stroke-width="1.5"
                stroke-linecap="round"
                stroke-linejoin="round"
                class="w-12 h-12 text-white"
              >
                <path
                  d="M17.8 19.2 16 11l3.5-3.5C21 6 21.5 4 21 3c-1-.5-3 0-4.5 1.5L13 8 4.8 6.2c-.5-.1-.9.1-1.1.5l-.3.5c-.2.5-.1 1 .3 1.3L9 12l-2 3H4l-1 1 3 2 2 3 1-1v-3l3-2 3.5 5.3c.3.4.8.5 1.3.3l.5-.2c.4-.3.6-.7.5-1.2z"
                />
              </svg>
            </div>
          </div>

          <!-- 폼 -->
          <div class="modal-form">
            <!-- 일정 이름 -->
            <div class="form-group">
              <label for="scheduleName" class="form-label">일정 이름</label>
              <div class="input-wrapper">
                <input
                  v-model="scheduleName"
                  id="scheduleName"
                  class="form-input"
                  type="text"
                  placeholder="예: 제주도 3박 4일 여행"
                  @focus="inputFocus = 'name'"
                  @blur="inputFocus = null"
                  :class="{ 'input-focused': inputFocus === 'name' }"
                />
                <svg
                  v-if="scheduleName"
                  @click="scheduleName = ''"
                  xmlns="http://www.w3.org/2000/svg"
                  width="20"
                  height="20"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  class="input-clear-icon"
                >
                  <circle cx="12" cy="12" r="10"></circle>
                  <line x1="15" y1="9" x2="9" y2="15"></line>
                  <line x1="9" y1="9" x2="15" y2="15"></line>
                </svg>
              </div>
            </div>

            <!-- 여행 일수 -->
            <div class="form-group">
              <label for="days" class="form-label">여행 일수</label>
              <div class="days-input-wrapper">
                <button
                  @click="decrementDays"
                  class="days-btn"
                  :disabled="days <= 1"
                  :class="{ 'opacity-50 cursor-not-allowed': days <= 1 }"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="20"
                    height="20"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    stroke-width="2"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  >
                    <line x1="5" y1="12" x2="19" y2="12"></line>
                  </svg>
                </button>
                <input
                  v-model.number="days"
                  id="days"
                  class="days-input"
                  type="number"
                  min="1"
                  max="30"
                  @focus="inputFocus = 'days'"
                  @blur="inputFocus = null"
                  :class="{ 'input-focused': inputFocus === 'days' }"
                />
                <button
                  @click="incrementDays"
                  class="days-btn"
                  :disabled="days >= 30"
                  :class="{ 'opacity-50 cursor-not-allowed': days >= 30 }"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="20"
                    height="20"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    stroke-width="2"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  >
                    <line x1="12" y1="5" x2="12" y2="19"></line>
                    <line x1="5" y1="12" x2="19" y2="12"></line>
                  </svg>
                </button>
                <span class="days-label">일</span>
              </div>
            </div>

            <!-- 여행 시작일 (선택사항) -->
            <div class="form-group">
              <label for="startDate" class="form-label">
                여행 시작일 <span class="text-gray-400 text-sm">(선택사항)</span>
              </label>
              <div class="input-wrapper">
                <input
                  v-model="startDate"
                  id="startDate"
                  class="form-input"
                  type="date"
                  @focus="inputFocus = 'date'"
                  @blur="inputFocus = null"
                  :class="{ 'input-focused': inputFocus === 'date' }"
                />
              </div>
            </div>
          </div>

          <!-- 버튼 -->
          <div class="modal-actions">
            <button @click="close" class="cancel-btn">취소</button>
            <button
              @click="submit"
              class="submit-btn"
              :disabled="!isValid"
              :class="{ 'opacity-70 cursor-not-allowed': !isValid }"
            >
              계획 만들기
            </button>
          </div>
        </div>
      </div>
    </transition>
  </teleport>
</template>

<script setup>
import { ref, watch, computed, defineProps, defineEmits } from 'vue'

const props = defineProps({
  modelValue: { type: Boolean, required: true },
})
const emits = defineEmits(['update:modelValue', 'submit'])

const visible = ref(props.modelValue)
watch(
  () => props.modelValue,
  (v) => (visible.value = v),
)

const scheduleName = ref('')
const days = ref(3)
const startDate = ref('')
const inputFocus = ref(null)

const isValid = computed(() => {
  return scheduleName.value.trim() !== '' && days.value >= 1 && days.value <= 30
})

function close() {
  emits('update:modelValue', false)
}

function submit() {
  if (!isValid.value) return

  emits('submit', {
    name: scheduleName.value,
    days: days.value,
    startDate: startDate.value || null,
  })

  // Reset form
  scheduleName.value = ''
  days.value = 3
  startDate.value = ''

  close()
}

function incrementDays() {
  if (days.value < 30) days.value++
}

function decrementDays() {
  if (days.value > 1) days.value--
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
  backdrop-filter: blur(2px);
}

.modal-container {
  width: 450px;
  background: #fff;
  border-radius: 16px;
  box-shadow:
    0 20px 25px -5px rgba(0, 0, 0, 0.1),
    0 10px 10px -5px rgba(0, 0, 0, 0.04);
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.5rem;
  border-bottom: 1px solid #f3f4f6;
}

.modal-title {
  font-size: 1.25rem;
  font-weight: 600;
  color: #111827;
}

.modal-close-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 2rem;
  height: 2rem;
  border-radius: 9999px;
  color: #6b7280;
  transition: all 0.2s;
}

.modal-close-btn:hover {
  background-color: #f3f4f6;
  color: #111827;
}

.modal-illustration {
  display: flex;
  justify-content: center;
  padding: 2rem 0;
  background: linear-gradient(to bottom, #f9fafb, #ffffff);
}

.illustration-circle {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100px;
  height: 100px;
  border-radius: 9999px;
  background: #3c64b1;
  box-shadow: 0 10px 15px -3px rgba(60, 100, 177, 0.3);
}

.modal-form {
  padding: 1.5rem;
}

.form-group {
  margin-bottom: 1.25rem;
}

.form-label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 500;
  color: #374151;
}

.input-wrapper {
  position: relative;
}

.form-input {
  width: 100%;
  padding: 0.75rem 1rem;
  border: 1px solid #d1d5db;
  border-radius: 0.5rem;
  font-size: 1rem;
  transition: all 0.2s;
}

.form-input:focus {
  outline: none;
  border-color: #3c64b1;
  box-shadow: 0 0 0 3px rgba(60, 100, 177, 0.2);
}

.input-focused {
  border-color: #3c64b1;
  box-shadow: 0 0 0 3px rgba(60, 100, 177, 0.2);
}

.input-clear-icon {
  position: absolute;
  right: 0.75rem;
  top: 50%;
  transform: translateY(-50%);
  color: #9ca3af;
  cursor: pointer;
}

.input-clear-icon:hover {
  color: #6b7280;
}

.days-input-wrapper {
  display: flex;
  align-items: center;
}

.days-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 2.5rem;
  height: 2.5rem;
  background: #f3f4f6;
  border-radius: 0.5rem;
  color: #374151;
  transition: all 0.2s;
}

.days-btn:hover:not(:disabled) {
  background: #e5e7eb;
}

.days-input {
  width: 4rem;
  height: 2.5rem;
  margin: 0 0.5rem;
  border: 1px solid #d1d5db;
  border-radius: 0.5rem;
  text-align: center;
  font-size: 1rem;
  font-weight: 500;
  transition: all 0.2s;
  -moz-appearance: textfield;
}

.days-input::-webkit-outer-spin-button,
.days-input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

.days-input:focus {
  outline: none;
  border-color: #3c64b1;
  box-shadow: 0 0 0 3px rgba(60, 100, 177, 0.2);
}

.days-label {
  margin-left: 0.5rem;
  font-weight: 500;
  color: #374151;
}

.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 0.75rem;
  padding: 1.5rem;
  border-top: 1px solid #f3f4f6;
}

.cancel-btn {
  padding: 0.625rem 1.25rem;
  border: 1px solid #d1d5db;
  border-radius: 0.5rem;
  background: white;
  color: #374151;
  font-weight: 500;
  transition: all 0.2s;
}

.cancel-btn:hover {
  background: #f9fafb;
  border-color: #9ca3af;
}

.submit-btn {
  padding: 0.625rem 1.25rem;
  border-radius: 0.5rem;
  background: #3c64b1;
  color: white;
  font-weight: 500;
  transition: all 0.2s;
}

.submit-btn:hover:not(:disabled) {
  background: #2a4580;
}

/* 애니메이션 */
.modal-fade-enter-active,
.modal-fade-leave-active {
  transition: opacity 0.3s ease;
}

.modal-fade-enter-from,
.modal-fade-leave-to {
  opacity: 0;
}

.modal-fade-enter-active .modal-container {
  animation: modal-in 0.3s ease-out;
}

.modal-fade-leave-active .modal-container {
  animation: modal-out 0.2s ease-in forwards;
}

@keyframes modal-in {
  0% {
    transform: scale(0.95);
    opacity: 0;
  }
  100% {
    transform: scale(1);
    opacity: 1;
  }
}

@keyframes modal-out {
  0% {
    transform: scale(1);
    opacity: 1;
  }
  100% {
    transform: scale(0.95);
    opacity: 0;
  }
}
</style>
