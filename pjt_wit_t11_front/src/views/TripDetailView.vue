<template>
  <div class="min-h-screen flex flex-col bg-[#f6f8ff]">
    <!-- 네비게이션 바 -->
    <HeaderNavBar />

    <!-- 여행 상세 컨텐츠 -->
    <div class="container mx-auto px-4 py-8 flex-1">
      <div class="max-w-6xl mx-auto">
        <!-- 뒤로가기 버튼 -->
        <button
          @click="goBack"
          class="flex items-center text-gray-600 hover:text-[#3c64b1] mb-6 transition-colors"
        >
          <svg
            class="w-5 h-5 mr-1"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 20 20"
            fill="currentColor"
          >
            <path
              fill-rule="evenodd"
              d="M9.707 16.707a1 1 0 01-1.414 0l-6-6a1 1 0 010-1.414l6-6a1 1 0 011.414 1.414L5.414 9H17a1 1 0 110 2H5.414l4.293 4.293a1 1 0 010 1.414z"
              clip-rule="evenodd"
            />
          </svg>
          내 여행으로 돌아가기
        </button>

        <!-- 로딩 상태 -->
        <div v-if="isLoading" class="flex justify-center items-center h-64">
          <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
        </div>

        <!-- 여행 정보 -->
        <div v-else-if="tripData" class="space-y-6">
          <!-- 여행 헤더 정보 -->
          <div class="bg-white rounded-lg shadow-md overflow-hidden">
            <div class="relative h-64 bg-gradient-to-r from-blue-500 to-blue-600">
              <img
                v-if="getFirstImage()"
                :src="getFirstImage()"
                :alt="tripData.tripName"
                class="w-full h-full object-cover"
              />
              <div class="absolute inset-0 bg-black bg-opacity-30 flex items-end">
                <div class="p-6 text-white w-full">
                  <div class="flex justify-between items-end">
                    <div>
                      <h1 class="text-3xl font-bold mb-2">{{ tripData.tripName }}</h1>
                      <p class="text-lg opacity-90">{{ formatDateRange() }}</p>
                    </div>
                    <div class="text-right">
                      <div class="bg-white bg-opacity-20 rounded-lg p-3 backdrop-blur-sm">
                        <div class="text-sm opacity-90">여행 기간</div>
                        <div class="text-xl font-semibold">{{ tripData.tripDays }}일</div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- 여행 통계 -->
            <div class="p-6 border-b border-gray-200">
              <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
                <div class="text-center">
                  <div class="text-2xl font-bold text-blue-600">{{ tripData.tripDays }}</div>
                  <div class="text-sm text-gray-500">총 일수</div>
                </div>
                <div class="text-center">
                  <div class="text-2xl font-bold text-green-600">{{ getTotalPlaces() }}</div>
                  <div class="text-sm text-gray-500">총 장소</div>
                </div>
                <div class="text-center">
                  <div class="text-2xl font-bold text-purple-600">
                    {{ tripData.accommodations?.length || 0 }}
                  </div>
                  <div class="text-sm text-gray-500">등록된 숙소</div>
                </div>
                <div class="text-center">
                  <div class="text-2xl font-bold text-orange-600">
                    <span
                      class="px-3 py-1 text-sm font-medium rounded-full bg-blue-100 text-blue-800"
                    >
                      계획됨
                    </span>
                  </div>
                  <div class="text-sm text-gray-500">상태</div>
                </div>
              </div>
            </div>

            <!-- 여행 메타 정보 -->
            <div class="p-6">
              <div class="flex items-center justify-between text-sm text-gray-500">
                <div class="flex items-center">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    class="h-4 w-4 mr-1"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M8 7V3a1 1 0 011-1h6a1 1 0 011 1v4m-6 0h6m-6 0V7a1 1 0 00-1 1v10a1 1 0 001 1h8a1 1 0 001-1V8a1 1 0 00-1-1h-2"
                    />
                  </svg>
                  {{ formatDate(tripData.tripStart) }} 시작 예정
                </div>
                <div class="flex items-center">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    class="h-4 w-4 mr-1"
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
                  총 {{ getTotalPlaces() }}개 장소 방문 예정
                </div>
              </div>
            </div>
          </div>

          <!-- 일차별 여행 계획 -->
          <div class="space-y-6">
            <div
              v-for="(dayKey, dayIndex) in getDayKeys()"
              :key="dayKey"
              class="bg-white rounded-lg shadow-md overflow-hidden"
            >
              <!-- 일차 헤더 -->
              <div
                class="bg-gradient-to-r from-blue-50 to-blue-100 px-6 py-4 border-b border-blue-200"
              >
                <div class="flex items-center justify-between">
                  <h2 class="text-xl font-semibold text-blue-800">
                    {{ dayIndex + 1 }}일차
                    <span class="text-sm font-normal text-blue-600 ml-2">
                      ({{ formatDayDate(dayIndex) }})
                    </span>
                  </h2>
                  <div class="flex items-center text-sm text-blue-600">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      class="h-4 w-4 mr-1"
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
                    {{ tripData.itinerary[dayKey]?.length || 0 }}개 장소
                  </div>
                </div>
              </div>

              <div class="p-6">
                <!-- 시작 숙소 정보 -->
                <div
                  v-if="getStartAccommodation(dayKey)"
                  class="mb-6 p-4 bg-green-50 border border-green-200 rounded-lg"
                >
                  <div class="flex items-center mb-2">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      class="h-5 w-5 text-green-600 mr-2"
                      fill="none"
                      viewBox="0 0 24 24"
                      stroke="currentColor"
                    >
                      <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-7a1 1 0 011-1h2a1 1 0 011 1v7a1 1 0 001 1m-6 0h6"
                      />
                    </svg>
                    <h4 class="font-semibold text-green-800">시작 숙소</h4>
                  </div>
                  <div class="text-sm">
                    <p class="font-medium text-gray-900">
                      {{ getStartAccommodation(dayKey).name }}
                    </p>
                    <p class="text-gray-600">{{ getStartAccommodation(dayKey).address }}</p>
                    <p v-if="getStartAccommodation(dayKey).phone" class="text-gray-500">
                      {{ getStartAccommodation(dayKey).phone }}
                    </p>
                  </div>
                </div>

                <!-- 장소 목록 -->
                <div class="space-y-4">
                  <div
                    v-for="(place, placeIndex) in tripData.itinerary[dayKey] || []"
                    :key="place.instanceId || placeIndex"
                    class="flex items-start space-x-4 p-4 border border-gray-200 rounded-lg hover:border-blue-300 transition-colors"
                  >
                    <!-- 순서 번호 -->
                    <div class="flex-shrink-0">
                      <div
                        class="w-8 h-8 bg-blue-600 text-white rounded-full flex items-center justify-center text-sm font-semibold"
                      >
                        {{ placeIndex + 1 }}
                      </div>
                    </div>

                    <!-- 장소 이미지 -->
                    <div class="flex-shrink-0">
                      <img
                        :src="place.firstImage1 || 'https://placehold.co/80x80?text=장소'"
                        :alt="place.title"
                        class="w-16 h-16 rounded-lg object-cover"
                        @error="handleImageError"
                      />
                    </div>

                    <!-- 장소 정보 -->
                    <div class="flex-1 min-w-0">
                      <div class="flex items-start justify-between">
                        <div class="flex-1">
                          <h3 class="text-lg font-semibold text-gray-900 truncate">
                            <a
                              :href="place.homepage ? place.homepage : '#'"
                              :class="{ 'pointer-events-none text-400': !place.homepage }"
                              target="_blank"
                              rel="noopener noreferrer"
                              >{{ place.title }}</a
                            >
                          </h3>
                          <p class="text-sm text-gray-600 mt-1">
                            {{ place.time }}
                          </p>

                          <!-- 시간 및 체류 정보 -->
                          <div class="flex items-center mt-2 space-x-4">
                            <span
                              class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-blue-100 text-blue-800"
                            >
                              {{ getContentTypeName(place.contentTypeId) }}
                            </span>
                            <div v-if="place.time" class="flex items-center text-sm text-gray-500">
                              <svg
                                xmlns="http://www.w3.org/2000/svg"
                                class="h-4 w-4 mr-1"
                                fill="none"
                                viewBox="0 0 24 24"
                                stroke="currentColor"
                              >
                                <path
                                  stroke-linecap="round"
                                  stroke-linejoin="round"
                                  stroke-width="2"
                                  d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"
                                />
                              </svg>
                              다음 장소까지 이동 시간 : {{ formatTravelTime(place.travelCar) }}
                            </div>

                            <div
                              v-if="getStayDuration(place)"
                              class="flex items-center text-sm text-gray-500"
                            >
                              <svg
                                xmlns="http://www.w3.org/2000/svg"
                                class="h-4 w-4 mr-1"
                                fill="none"
                                viewBox="0 0 24 24"
                                stroke="currentColor"
                              >
                                <path
                                  stroke-linecap="round"
                                  stroke-linejoin="round"
                                  stroke-width="2"
                                  d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"
                                />
                              </svg>
                              머무는 시간: {{ getStayDuration(place) }}
                            </div>
                          </div>
                        </div>

                        <!-- 평점 및 리뷰 -->
                        <div class="flex flex-col items-end space-y-1">
                          <div v-if="place.rating > 0" class="flex items-center">
                            <svg
                              xmlns="http://www.w3.org/2000/svg"
                              class="h-4 w-4 text-yellow-400 mr-1"
                              viewBox="0 0 20 20"
                              fill="currentColor"
                            >
                              <path
                                d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"
                              />
                            </svg>
                            <span class="text-sm text-gray-600">{{ place.rating }}</span>
                          </div>
                          <div
                            v-if="place.reviewCount > 0"
                            class="flex items-center text-sm text-gray-500"
                          >
                            <svg
                              xmlns="http://www.w3.org/2000/svg"
                              class="h-4 w-4 mr-1"
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
                            리뷰 {{ place.reviewCount }}개
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>

                  <!-- 이동 시간 표시 (마지막 장소가 아닌 경우) -->
                  <div
                    v-if="placeIndex < (tripData.itinerary[dayKey]?.length || 0) - 1"
                    class="flex items-center justify-center py-2"
                  >
                    <div class="flex items-center text-sm text-gray-500">
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        class="h-4 w-4 mr-1"
                        fill="none"
                        viewBox="0 0 24 24"
                        stroke="currentColor"
                      >
                        <path
                          stroke-linecap="round"
                          stroke-linejoin="round"
                          stroke-width="2"
                          d="M19 14l-7 7m0 0l-7-7m7 7V3"
                        />
                      </svg>
                      <span
                        >이동 시간:
                        {{ getTravelTime(tripData.itinerary[dayKey][placeIndex]) }}</span
                      >
                    </div>
                  </div>
                </div>

                <!-- 종료 숙소 정보 -->
                <div
                  v-if="getEndAccommodation(dayKey)"
                  class="mt-6 p-4 bg-red-50 border border-red-200 rounded-lg"
                >
                  <div class="flex items-center mb-2">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      class="h-5 w-5 text-red-600 mr-2"
                      fill="none"
                      viewBox="0 0 24 24"
                      stroke="currentColor"
                    >
                      <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-7a1 1 0 011-1h2a1 1 0 011 1v7a1 1 0 001 1m-6 0h6"
                      />
                    </svg>
                    <h4 class="font-semibold text-red-800">종료 숙소</h4>
                  </div>
                  <div class="text-sm">
                    <p class="font-medium text-gray-900">{{ getEndAccommodation(dayKey).name }}</p>
                    <p class="text-gray-600">{{ getEndAccommodation(dayKey).address }}</p>
                    <p v-if="getEndAccommodation(dayKey).phone" class="text-gray-500">
                      {{ getEndAccommodation(dayKey).phone }}
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 액션 버튼들 -->
          <div class="mt-8 flex justify-center space-x-4">
            <button
              @click="editTrip"
              class="bg-blue-600 text-white px-6 py-3 rounded-md hover:bg-blue-700 transition-colors shadow-sm flex items-center"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-5 w-5 mr-2"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"
                />
              </svg>
              여행 수정하기
            </button>
            <button
              @click="shareTrip"
              class="bg-green-600 text-white px-6 py-3 rounded-md hover:bg-green-700 transition-colors shadow-sm flex items-center"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-5 w-5 mr-2"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M8.684 13.342C8.886 12.938 9 12.482 9 12c0-.482-.114-.938-.316-1.342m0 2.684a3 3 0 110-2.684m0 2.684l6.632 3.316m-6.632-6l6.632-3.316m0 0a3 3 0 105.367-2.684 3 3 0 00-5.367 2.684zm0 9.316a3 3 0 105.367 2.684 3 3 0 00-5.367-2.684z"
                />
              </svg>
              여행 공유하기
            </button>
          </div>
        </div>

        <!-- 에러 상태 -->
        <div v-else-if="error" class="text-center py-12">
          <div class="text-red-500 mb-4">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="h-12 w-12 mx-auto"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-2.5L13.732 4c-.77-.833-1.964-.833-2.732 0L4.082 16.5c-.77.833.192 2.5 1.732 2.5z"
              />
            </svg>
          </div>
          <h3 class="text-lg font-medium text-gray-900 mb-2">여행 정보를 불러올 수 없습니다</h3>
          <p class="text-gray-500 mb-4">{{ error }}</p>
          <button
            @click="goBack"
            class="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition-colors"
          >
            목록으로 돌아가기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import HeaderNavBar from '@/components/HeaderNavBar.vue'
import axios from 'axios'

const router = useRouter()
const route = useRoute()
const savedScheduledId = route.params.scheduleId

const tripData = ref({
  scheduleId: savedScheduledId,
  itinerary: {},
  accommodations: [],
  dayAccommodations: {},
  tripStart: '',
  tripDays: '',
})
const isLoading = ref(true)
const error = ref(null)

const loadTripData = async () => {
  try {
    isLoading.value = true
    error.value = null

    const token = localStorage.getItem('jwt')
    if (!token) {
      throw new Error('로그인이 필요합니다.')
    }

    const response = await axios.get(`/api/user/schedule/${savedScheduledId}`, {
      headers: { Authorization: `Bearer ${token}` },
    })

    tripData.value = response.data
    console.log('여행 정보 (API):', response.data)
  } catch (err) {
    console.error('여행 정보 로딩 실패:', err)
    error.value =
      err.response?.data?.message || err.message || '여행 정보를 불러오는 중 오류가 발생했습니다.'
  } finally {
    isLoading.value = false
  }
}

onMounted(async () => {
  await loadTripData()
})

// 첫 번째 이미지 가져오기
const getFirstImage = () => {
  if (!tripData.value?.itinerary) return null

  for (const dayKey of Object.keys(tripData.value.itinerary)) {
    const places = tripData.value.itinerary[dayKey]
    for (const place of places) {
      if (place.firstImage1) {
        return place.firstImage1
      }
    }
  }
  return null
}

// 총 장소 수 계산
const getTotalPlaces = () => {
  if (!tripData.value?.itinerary) return 0

  return Object.values(tripData.value.itinerary).reduce((total, places) => {
    return total + (places?.length || 0)
  }, 0)
}

// 일차 키 배열 가져오기 (정렬된)
const getDayKeys = () => {
  if (!tripData.value?.itinerary) return []

  return Object.keys(tripData.value.itinerary).sort((a, b) => {
    const dayA = parseInt(a.replace('day', ''))
    const dayB = parseInt(b.replace('day', ''))
    return dayA - dayB
  })
}

watch(
  () => tripData.value.itinerary,
  (newItinerary) => {
    if (newItinerary && Object.keys(newItinerary).length) {
      recalcAllScheduleTimes(tripData.value, 'car')
    }
  },
  { immediate: true },
)

onMounted(() => {
  const passed = history.state.schedules
  if (passed) {
    tripData.value = passed
    console.log('로드된 itinerary:', Object.keys(tripData.value.itinerary))
    recalcAllScheduleTimes(tripData.value, 'car') // ← 여기서 호출
  }
})

function parseHM(str) {
  const [h, m] = str.split(':').map(Number)
  return h * 60 + m
}

function formatHM(mins) {
  const h = Math.floor(mins / 60)
  const m = mins % 60
  return `${String(h).padStart(2, '0')}:${String(m).padStart(2, '0')}`
}

function recalcAllScheduleTimes(tripData, transportMode = 'car') {
  const startTimes = tripData.startTimes
  const dayAccommodations = tripData.dayAccommodations
  const itinerary = tripData.itinerary

  Object.keys(itinerary).forEach((dayKey) => {
    let cursor = parseHM(startTimes?.[dayKey] || '09:00') // 시작 시간 (문자열 → 분)
    const places = itinerary[dayKey]
    //const dayIndex = parseInt(dayKey.replace('day', '')) // 'day1' → 1

    // 시작 숙소
    const accForDay = dayAccommodations?.[dayKey] || []
    const startAcc = accForDay[0]
    const endAcc = accForDay[1]
    if (startAcc && startAcc?.index !== -1) {
      const stayMin = (startAcc.stayHour || 0) * 60 + (startAcc.stayMin || 0)
      const travelMin =
        Number(transportMode === 'car' ? startAcc.travelCar : startAcc.travelBus) || 0

      const start = cursor
      const end = start + stayMin
      startAcc.time = `${formatHM(start)}-${formatHM(end)}`
      cursor = end + travelMin
    }

    // 방문지들
    places.forEach((place) => {
      const stayMin = (place.stayHour || 0) * 60 + (place.stayMin || 0)
      const travelMin = Number(transportMode === 'car' ? place.travelCar : place.travelBus) || 0

      const start = cursor
      const end = start + stayMin
      place.time = `${formatHM(start)}-${formatHM(end)}`
      cursor = end + travelMin
    })

    // 종료 숙소
    if (endAcc && endAcc?.index !== -1) {
      const stayMin = (endAcc.stayHour || 0) * 60 + (endAcc.stayMin || 0)
      const start = cursor
      const end = start + stayMin
      endAcc.time = `${formatHM(start)}-${formatHM(end)}`
    }
  })
}

function formatTravelTime(minutes) {
  const h = Math.floor(minutes / 60)
  const m = minutes % 60
  return h > 0 ? `${h}시간 ${m}분` : `${m}분`
}

// 날짜 포맷팅
const formatDate = (dateString) => {
  if (!dateString) return ''

  const date = new Date(dateString)
  return `${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일`
}

// 날짜 범위 포맷팅
const formatDateRange = () => {
  if (!tripData.value?.tripStart || !tripData.value?.tripDays) return ''

  const startDate = new Date(tripData.value.tripStart)
  const endDate = new Date(startDate)
  endDate.setDate(startDate.getDate() + parseInt(tripData.value.tripDays) - 1)

  return `${formatDate(tripData.value.tripStart)} ~ ${formatDate(endDate.toISOString().split('T')[0])}`
}

// 특정 일차의 날짜 포맷팅
const formatDayDate = (dayIndex) => {
  if (!tripData.value?.tripStart) return ''

  const startDate = new Date(tripData.value.tripStart)
  const dayDate = new Date(startDate)
  dayDate.setDate(startDate.getDate() + dayIndex)

  return formatDate(dayDate.toISOString().split('T')[0])
}

// 시작 숙소 정보 가져오기
const getStartAccommodation = (dayKey) => {
  const dayAccom = tripData.value?.dayAccommodations?.[dayKey]
  if (!dayAccom[0] || dayAccom[0].index === undefined || dayAccom[0].index < 0) return null
  return tripData.value?.accommodations?.[dayAccom[0].index] || null
}

// 종료 숙소 정보 가져오기 (현재 구조에서는 시작과 동일)
const getEndAccommodation = (dayKey) => {
  const dayAccom = tripData.value?.dayAccommodations?.[dayKey]
  if (!dayAccom[1] || dayAccom[1].index === undefined || dayAccom[1].index < 0) return null
  return tripData.value?.accommodations?.[dayAccom[1].index] || null
}

// 체류 시간 포맷팅
const getStayDuration = (place) => {
  const hours = place.stayHour || 0
  const minutes = place.stayMin || 0

  if (hours === 0 && minutes === 0) return null

  if (hours > 0 && minutes > 0) {
    return `${hours}시간 ${minutes}분`
  } else if (hours > 0) {
    return `${hours}시간`
  } else {
    return `${minutes}분`
  }
}

// 이동 시간 포맷팅
const getTravelTime = (place) => {
  if (place.travelCar > 0) {
    return `자차 ${place.travelCar}분`
  } else if (place.travelBus > 0) {
    return `대중교통 ${place.travelBus}분`
  }
  return '15분'
}

// 콘텐츠 타입명 가져오기
const getContentTypeName = (contentTypeId) => {
  const types = {
    1: '카페',
    2: '음식점',
    12: '관광지',
    14: '문화시설',
    15: '축제공연행사',
    25: '여행코스',
    28: '레포츠',
    32: '숙박',
    38: '쇼핑',
    39: '음식점',
  }
  return types[contentTypeId] || '기타'
}

// 이미지 에러 처리
const handleImageError = (event) => {
  event.target.src = 'https://placehold.co/80x80?text=이미지없음'
}

// 뒤로가기
const goBack = () => {
  router.push({ name: 'mypage' })
}

// 여행 수정
const editTrip = () => {
  localStorage.setItem('editTripData', JSON.stringify(tripData.value)) // ✅ 저장
  router.push({
    name: 'editSchedule',
    params: { scheduleId: savedScheduledId },
  })
}

// 여행 공유
const shareTrip = () => {
  alert('여행 공유 기능은 준비 중입니다.')
}
</script>

<style scoped>
.transition-colors {
  transition-property: background-color, border-color, color, fill, stroke;
  transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
  transition-duration: 150ms;
}

.animate-spin {
  animation: spin 1s linear infinite;
}

@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>
