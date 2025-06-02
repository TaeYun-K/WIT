<template>
  <teleport to="body">
    <transition name="modal-fade">
      <div v-if="visible" class="modal-overlay" @click.self="close">
        <div class="modal-container">
          <!-- 헤더 -->
          <div class="modal-header">
            <h2 class="modal-title">{{ place?.title }}</h2>
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

          <!-- 이미지 -->
          <div
            class="place-image-container flex justify-center items-center max-h-[50vh] overflow-auto"
          >
            <img
              :src="place.firstImage1 ? place.firstImage1 : getDefaultImage(place.contentTypeId)"
              :alt="place?.title"
              class="max-h-[80vh] w-auto object-contain rounded shadow-lg"
            />
            <div class="image-overlay">
              <div class="rating-badge">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="w-4 h-4 text-yellow-400"
                  viewBox="0 0 20 20"
                  fill="currentColor"
                >
                  <path
                    d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"
                  />
                </svg>
                <span class="rating-text">{{ place?.rating }}</span>
              </div>
              <div class="category-badge">{{ getContentTypeName(place?.contentTypeId) }}</div>
            </div>
          </div>

          <!-- 콘텐츠 -->
          <div class="modal-content">
            <!-- 기본 정보 -->
            <div class="info-section">
              <div class="info-item">
                <div class="info-icon">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    class="w-5 h-5"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"
                    />
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"
                    />
                  </svg>
                </div>
                <div class="info-content">
                  <div class="info-label">주소</div>
                  <div class="info-value">{{ place?.addr1 }}</div>
                </div>
              </div>

              <div class="info-item" v-if="place?.tel">
                <div class="info-icon">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    class="w-5 h-5"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z"
                    />
                  </svg>
                </div>
                <div class="info-content">
                  <div class="info-label">전화번호</div>
                  <div class="info-value">{{ place?.tel }}</div>
                </div>
              </div>

              <div class="info-item" v-if="place?.homepage">
                <div class="info-icon">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    class="w-5 h-5"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M21 12a9 9 0 01-9 9m9-9a9 9 0 00-9-9m9 9H3m9 9a9 9 0 01-9-9m9 9c1.657 0 3-4.03 3-9s-1.343-9-3-9m0 18c-1.657 0-3-4.03-3-9s1.343-9 3-9m-9 9a9 9 0 019-9"
                    />
                  </svg>
                </div>
                <div class="info-content">
                  <div class="info-label">홈페이지</div>
                  <a
                    :href="place?.homepage"
                    target="_blank"
                    rel="noopener noreferrer"
                    :title="place?.homepage"
                    class="text-blue-600 underline hover:text-blue-800 transition-colors inline-block max-w-[22rem] overflow-hidden whitespace-nowrap text-ellipsis"
                  >
                    {{ place?.homepage }}
                  </a>
                </div>
              </div>
            </div>

            <!-- 설명 -->
            <div class="description-section" v-if="place?.overview?.trim().length > 0">
              <h3 class="section-title">소개</h3>
              <p class="description-text">{{ place?.overview }}</p>
            </div>

            <!-- 통계 -->
            <div class="stats-section">
              <div class="stat-item">
                <div class="stat-icon">⭐</div>
                <div class="stat-content">
                  <div class="stat-value">{{ place?.rating }}</div>
                  <div class="stat-label">평점</div>
                </div>
              </div>
              <div class="stat-item">
                <div class="stat-icon">📝</div>
                <div class="stat-content">
                  <div class="stat-value">{{ place?.reviewCount || 0 }}</div>
                  <div class="stat-label">리뷰</div>
                </div>
              </div>
            </div>
          </div>

          <!-- 액션 버튼 -->
          <div class="modal-actions">
            <button @click="addToTrip" class="add-btn">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="w-5 h-5"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M12 4v16m8-8H4"
                />
              </svg>
              일정에 추가
            </button>
          </div>
        </div>
      </div>
    </transition>
  </teleport>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue'
import { getDefaultImage } from '@/utils/util'

const props = defineProps({
  visible: { type: Boolean, default: false },
  place: { type: Object, default: null },
})

const emits = defineEmits(['close', 'add-to-trip', 'add-to-wishlist'])

function close() {
  emits('close')
}

function addToTrip() {
  emits('add-to-trip', props.place)
  close()
}

const contentTypeMap = {
  1: '카페',
  2: '식당',
  12: '관광지',
  14: '문화시설',
  15: '축제공연행사',
  25: '여행코스',
  28: '레포츠',
  32: '숙박',
  38: '쇼핑',
  39: '관광지',
}

/**
 * 주어진 content_type_id 에 대응하는 이름을 반환합니다.
 * @param {number|string} id - content_type_id
 * @returns {string} 매핑된 이름 (없으면 '알 수 없음' 반환)
 */
function getContentTypeName(id) {
  // key가 string 일 수도 있으니, 객체 lookup 전에 `+` 또는 parseInt 처리
  const name = contentTypeMap[+id]
  return name != null ? name : '알 수 없음'
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
  width: 500px;
  max-height: 90vh;
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

.place-image-container {
  position: relative;
  height: 200px;
  overflow: hidden;
}

.place-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.image-overlay {
  position: absolute;
  top: 1rem;
  right: 1rem;
  display: flex;
  gap: 0.5rem;
}

.rating-badge {
  display: flex;
  align-items: center;
  gap: 0.25rem;
  background: rgba(0, 0, 0, 0.7);
  color: white;
  padding: 0.25rem 0.5rem;
  border-radius: 0.5rem;
  font-size: 0.875rem;
  font-weight: 500;
}

.rating-text {
  color: white;
}

.category-badge {
  background: #3c64b1;
  color: white;
  padding: 0.25rem 0.5rem;
  border-radius: 0.5rem;
  font-size: 0.875rem;
  font-weight: 500;
}

.modal-content {
  flex: 1;
  overflow-y: auto;
  padding: 1.5rem;
}

.info-section {
  margin-bottom: 1.5rem;
}

.info-item {
  display: flex;
  align-items: flex-start;
  gap: 0.75rem;
  margin-bottom: 1rem;
}

.info-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 2rem;
  height: 2rem;
  background: #f3f4f6;
  border-radius: 0.5rem;
  color: #6b7280;
  flex-shrink: 0;
}

.info-content {
  flex: 1;
}

.info-label {
  font-size: 0.875rem;
  color: #6b7280;
  margin-bottom: 0.25rem;
}

.info-value {
  color: #111827;
  font-weight: 500;
}

.info-link {
  color: #3c64b1;
  text-decoration: underline;
}

.info-link:hover {
  color: #2a4580;
}

.description-section {
  margin-bottom: 1.5rem;
}

.section-title {
  font-size: 1.125rem;
  font-weight: 600;
  color: #111827;
  margin-bottom: 0.75rem;
}

.description-text {
  color: #6b7280;
  line-height: 1.6;
}

.stats-section {
  display: flex;
  gap: 1rem;
  margin-bottom: 1.5rem;
  padding: 1rem;
  background: #f9fafb;
  border-radius: 0.75rem;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  flex: 1;
}

.stat-icon {
  font-size: 1.25rem;
}

.stat-content {
  text-align: center;
}

.stat-value {
  font-size: 1.125rem;
  font-weight: 600;
  color: #111827;
}

.stat-label {
  font-size: 0.75rem;
  color: #6b7280;
}

.reviews-section {
  margin-bottom: 1rem;
}

.reviews-container {
  space-y: 1rem;
}

.review-item {
  padding: 1rem;
  background: #f9fafb;
  border-radius: 0.75rem;
  margin-bottom: 0.75rem;
}

.review-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.5rem;
}

.reviewer-info {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.reviewer-avatar {
  width: 2rem;
  height: 2rem;
  background: #3c64b1;
  color: white;
  border-radius: 9999px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
  font-size: 0.875rem;
}

.reviewer-name {
  font-weight: 500;
  color: #111827;
  font-size: 0.875rem;
}

.review-date {
  font-size: 0.75rem;
  color: #6b7280;
}

.review-rating {
  display: flex;
  gap: 0.125rem;
}

.review-text {
  color: #6b7280;
  font-size: 0.875rem;
  line-height: 1.5;
}

.modal-actions {
  display: flex;
  gap: 0.75rem;
  padding: 1.5rem;
  border-top: 1px solid #f3f4f6;
}

.wishlist-btn {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.625rem 1.25rem;
  border: 1px solid #d1d5db;
  border-radius: 0.5rem;
  background: white;
  color: #374151;
  font-weight: 500;
  transition: all 0.2s;
  flex: 1;
  justify-content: center;
}

.wishlist-btn:hover {
  background: #f9fafb;
  border-color: #9ca3af;
}

.add-btn {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.625rem 1.25rem;
  border-radius: 0.5rem;
  background: #3c64b1;
  color: white;
  font-weight: 500;
  transition: all 0.2s;
  flex: 1;
  justify-content: center;
}

.add-btn:hover {
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
