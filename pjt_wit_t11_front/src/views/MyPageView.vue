<template>
  <div class="min-h-screen flex flex-col bg-[#f6f8ff]">
    <!-- 네비게이션 바 -->
    <HeaderNavBar />

    <!-- 마이페이지 컨텐츠 -->
    <div class="container mx-auto px-4 py-8 flex-1">
      <div class="max-w-4xl mx-auto">
        <!-- 프로필 섹션 -->
        <div class="bg-white rounded-lg shadow-md overflow-hidden mb-8">
          <div class="md:flex">
            <!-- 프로필 아이콘 섹션 -->
            <div
              class="md:flex-shrink-0 bg-gradient-to-r from-blue-500 to-blue-600 md:w-48 flex items-center justify-center p-8"
            >
              <div class="relative">
                <!-- 기본 사용자 아이콘 -->
                <div
                  class="h-32 w-32 rounded-full border-4 border-white bg-white flex items-center justify-center shadow-md"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    class="h-16 w-16 text-blue-600"
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
                </div>
              </div>
            </div>

            <!-- 프로필 정보 섹션 -->
            <div class="p-8 md:p-6 flex-1">
              <div class="flex justify-between items-start">
                <div>
                  <h2 class="text-2xl font-bold text-gray-800">
                    {{ user.username || '사용자' }}
                  </h2>
                  <p class="text-gray-600 mt-1">{{ user.email || 'user@example.com' }}</p>
                  <div class="flex items-center mt-3">
                    <span
                      class="bg-blue-100 text-blue-800 text-xs font-medium px-2.5 py-0.5 rounded"
                    >
                      회원
                    </span>
                    <span class="ml-2 text-sm text-gray-500">가입일: {{ user.createdAt }}</span>
                  </div>
                </div>
                <button
                  @click="activeTab = 'settings'"
                  class="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition-colors shadow-sm"
                >
                  설정
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- 탭 메뉴 -->
        <div class="mb-6 border-b border-gray-200">
          <nav class="flex space-x-8">
            <button
              @click="activeTab = 'overview'"
              :class="[
                'py-4 px-1 font-medium text-sm border-b-2 -mb-px whitespace-nowrap',
                activeTab === 'overview'
                  ? 'border-blue-600 text-blue-600'
                  : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300',
              ]"
            >
              개요
            </button>
            <button
              @click="activeTab = 'trips'"
              :class="[
                'py-4 px-1 font-medium text-sm border-b-2 -mb-px whitespace-nowrap',
                activeTab === 'trips'
                  ? 'border-blue-600 text-blue-600'
                  : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300',
              ]"
            >
              내 여행
            </button>
            <button
              @click="activeTab = 'settings'"
              :class="[
                'py-4 px-1 font-medium text-sm border-b-2 -mb-px whitespace-nowrap',
                activeTab === 'settings'
                  ? 'border-blue-600 text-blue-600'
                  : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300',
              ]"
            >
              설정
            </button>
          </nav>
        </div>

        <!-- 개요 탭 -->
        <div v-if="activeTab === 'overview'" class="space-y-6">
          <!-- 빠른 시작 가이드 -->
          <div class="bg-white rounded-lg shadow-md p-6">
            <h3 class="text-lg font-medium text-gray-900 mb-4">빠른 시작</h3>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div
                class="border border-gray-200 rounded-lg p-4 hover:border-blue-300 transition-colors cursor-pointer"
                @click="goToNewTrip"
              >
                <div class="flex items-center">
                  <div class="flex-shrink-0">
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
                  <div class="ml-3">
                    <h4 class="text-sm font-medium text-gray-900">새 여행 계획</h4>
                    <p class="text-sm text-gray-500">여행 일정을 계획해보세요</p>
                  </div>
                </div>
              </div>

              <div
                class="border border-gray-200 rounded-lg p-4 hover:border-blue-300 transition-colors cursor-pointer"
                @click="goToBoard"
              >
                <div class="flex items-center">
                  <div class="flex-shrink-0">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      class="h-8 w-8 text-green-600"
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
                  </div>
                  <div class="ml-3">
                    <h4 class="text-sm font-medium text-gray-900">여행 커뮤니티</h4>
                    <p class="text-sm text-gray-500">다른 여행자들과 소통하세요</p>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 계정 정보 요약 -->
          <div class="bg-white rounded-lg shadow-md p-6">
            <h3 class="text-lg font-medium text-gray-900 mb-4">계정 정보</h3>
            <div class="space-y-3">
              <div class="flex justify-between items-center py-2 border-b border-gray-100">
                <span class="text-sm font-medium text-gray-500">이름</span>
                <span class="text-sm text-gray-900">{{ user.username || '사용자' }}</span>
              </div>
              <div class="flex justify-between items-center py-2 border-b border-gray-100">
                <span class="text-sm font-medium text-gray-500">이메일</span>
                <span class="text-sm text-gray-900">{{
                  userStore.user.email || 'user@example.com'
                }}</span>
              </div>
              <div class="flex justify-between items-center py-2">
                <span class="text-sm font-medium text-gray-500">가입일</span>
                <span class="text-sm text-gray-900">{{ user.createdAt }}</span>
              </div>
            </div>
          </div>

          <!-- 도움말 및 지원 -->
          <div class="bg-white rounded-lg shadow-md p-6">
            <h3 class="text-lg font-medium text-gray-900 mb-4">도움말 및 지원</h3>
            <div class="space-y-3">
              <a
                href="#"
                class="flex items-center text-sm text-gray-600 hover:text-blue-600 transition-colors"
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
                    d="M8.228 9c.549-1.165 2.03-2 3.772-2 2.21 0 4 1.343 4 3 0 1.4-1.278 2.575-3.006 2.907-.542.104-.994.54-.994 1.093m0 3h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                  />
                </svg>
                자주 묻는 질문
              </a>
              <a
                href="#"
                class="flex items-center text-sm text-gray-600 hover:text-blue-600 transition-colors"
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
                    d="M3 8l7.89 4.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"
                  />
                </svg>
                고객 지원 문의
              </a>
              <a
                href="#"
                class="flex items-center text-sm text-gray-600 hover:text-blue-600 transition-colors"
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
                    d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.746 0 3.332.477 4.5 1.253v13C19.832 18.477 18.246 18 16.5 18c-1.746 0-3.332.477-4.5 1.253"
                  />
                </svg>
                사용 가이드
              </a>
            </div>
          </div>
        </div>

        <!-- 내 여행 탭 -->
        <div v-else-if="activeTab === 'trips'" class="space-y-6">
          <!-- 여행 목록 헤더 -->
          <div class="flex justify-between items-center">
            <h3 class="text-lg font-medium text-gray-900">내 여행 목록</h3>
            <button
              @click="goToNewTrip"
              class="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition-colors shadow-sm flex items-center"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-5 w-5 mr-1"
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
              새 여행 계획
            </button>
          </div>

          <!-- 여행 목록 -->
          <div
            v-if="userStore.user.schedules.length > 0"
            class="grid grid-cols-1 md:grid-cols-2 gap-6"
          >
            <div
              v-for="schedule in userStore.user.schedules"
              :key="schedule.scheduleId"
              class="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow"
            >
              <!-- 여행 이미지 -->
              <div class="h-48 bg-gradient-to-r from-blue-400 to-blue-600 relative">
                <img
                  v-if="schedule.image"
                  :src="schedule.image"
                  :alt="schedule.title"
                  class="w-full h-full object-cover"
                />
                <div class="absolute inset-0 bg-black bg-opacity-20 flex items-end">
                  <div class="p-4 text-white">
                    <h4 class="text-lg font-semibold">{{ schedule.title }}</h4>
                    <p class="text-sm opacity-90">{{ schedule.dayCount }}일 여행</p>
                  </div>
                </div>
              </div>

              <!-- 여행 정보 -->
              <div class="p-4">
                <div class="flex items-center justify-between mb-3">
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
                        d="M8 7V3a1 1 0 011-1h6a1 1 0 011 1v4m-6 0h6m-6 0V7a1 1 0 00-1 1v10a1 1 0 001 1h8a1 1 0 001-1V8a1 1 0 00-1-1h-2"
                      />
                    </svg>
                    {{ formatDate(schedule.startDate) }} 생성
                  </div>
                  <span
                    :class="[
                      'px-2 py-1 text-xs font-medium rounded-full',
                      schedule.status === 'completed'
                        ? 'bg-green-100 text-green-800'
                        : schedule.status === 'ongoing'
                          ? 'bg-blue-100 text-blue-800'
                          : 'bg-gray-100 text-gray-800',
                    ]"
                  >
                    {{ getStatusText(schedule.status) }}
                  </span>
                </div>

                <p class="text-gray-600 text-sm mb-4 line-clamp-2">{{ schedule.description }}</p>

                <!-- 여행 통계 -->
                <div class="flex items-center justify-between text-sm text-gray-500 mb-4">
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
                    {{ schedule.placeCount }}개 장소
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
                        d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"
                      />
                    </svg>
                    {{ schedule.dayCount }}일
                  </div>
                </div>

                <!-- 액션 버튼들 -->
                <div class="flex space-x-2">
                  <button
                    @click="viewTrip(schedule)"
                    class="flex-1 bg-blue-600 text-white py-2 px-3 rounded-md hover:bg-blue-700 transition-colors text-sm font-medium"
                  >
                    보기
                  </button>
                  <button
                    @click="editTrip(schedule)"
                    class="flex-1 bg-gray-600 text-white py-2 px-3 rounded-md hover:bg-gray-700 transition-colors text-sm font-medium"
                  >
                    수정
                  </button>
                  <button
                    @click="confirmDeleteTrip(schedule)"
                    class="flex-1 bg-red-600 text-white py-2 px-3 rounded-md hover:bg-red-700 transition-colors text-sm font-medium"
                  >
                    삭제
                  </button>
                </div>
              </div>
            </div>
          </div>

          <!-- 여행 없음 상태 -->
          <div v-else class="text-center py-12">
            <svg
              class="mx-auto h-12 w-12 text-gray-400"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
              aria-hidden="true"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M9 20l-5.447-2.724A1 1 0 013 16.382V5.618a1 1 0 011.447-.894L9 7m0 13l6-3m-6 3V7m6 10l4.553 2.276A1 1 0 0021 18.382V7.618a1 1 0 00-.553-.894L15 4m0 13V4m0 0L9 7"
              />
            </svg>
            <h3 class="mt-2 text-sm font-medium text-gray-900">여행 계획이 없습니다</h3>
            <p class="mt-1 text-sm text-gray-500">첫 번째 여행 계획을 만들어보세요.</p>
            <div class="mt-6">
              <button
                @click="goToNewTrip"
                class="inline-flex items-center px-4 py-2 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
              >
                <svg
                  class="-ml-1 mr-2 h-5 w-5"
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 20 20"
                  fill="currentColor"
                  aria-hidden="true"
                >
                  <path
                    fill-rule="evenodd"
                    d="M10 3a1 1 0 011 1v5h5a1 1 0 110 2h-5v5a1 1 0 11-2 0v-5H4a1 1 0 110-2h5V4a1 1 0 011-1z"
                    clip-rule="evenodd"
                  />
                </svg>
                새 여행 계획 만들기
              </button>
              <Modal v-model="showModal" @submit="onCreate" />
            </div>
          </div>
        </div>

        <!-- 설정 탭 -->
        <div v-else-if="activeTab === 'settings'" class="space-y-8">
          <div class="bg-white rounded-lg shadow-md overflow-hidden">
            <div class="px-6 py-5 border-b border-gray-200">
              <h3 class="text-lg font-medium leading-6 text-gray-900">계정 설정</h3>
            </div>
            <div class="px-6 py-5">
              <div class="space-y-6">
                <div class="grid grid-cols-1 gap-y-6 gap-x-4 sm:grid-cols-6">
                  <div class="sm:col-span-3">
                    <label for="username" class="block text-sm font-medium text-gray-700"
                      >이름</label
                    >
                    <div class="mt-1">
                      <input
                        type="text"
                        name="username"
                        id="username"
                        v-model="userSettings.name"
                        class="shadow-sm focus:ring-blue-500 focus:border-blue-500 block w-full sm:text-sm border-gray-300 rounded-md"
                      />
                    </div>
                  </div>

                  <div class="sm:col-span-3">
                    <label for="email" class="block text-sm font-medium text-gray-700"
                      >이메일</label
                    >
                    <div class="mt-1">
                      <input
                        type="email"
                        name="email"
                        id="email"
                        v-model="userSettings.email"
                        class="shadow-sm focus:ring-blue-500 focus:border-blue-500 block w-full sm:text-sm border-gray-300 rounded-md"
                      />
                    </div>
                  </div>

                  <div class="sm:col-span-6">
                    <label for="password" class="block text-sm font-medium text-gray-700"
                      >새 비밀번호</label
                    >
                    <div class="mt-1">
                      <input
                        type="password"
                        name="password"
                        id="password"
                        v-model="userSettings.newPassword"
                        placeholder="새 비밀번호를 입력하세요 (변경 시에만)"
                        class="shadow-sm focus:ring-blue-500 focus:border-blue-500 block w-full sm:text-sm border-gray-300 rounded-md"
                      />
                    </div>
                    <p class="mt-2 text-sm text-gray-500">
                      비밀번호를 변경하려면 새 비밀번호를 입력하세요.
                    </p>
                  </div>
                </div>

                <div class="flex justify-end">
                  <button
                    type="button"
                    class="bg-white py-2 px-4 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition-colors"
                  >
                    취소
                  </button>
                  <button
                    type="submit"
                    @click="saveSettings"
                    class="ml-3 inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition-colors"
                  >
                    저장
                  </button>
                </div>
              </div>
            </div>
          </div>

          <div class="bg-white rounded-lg shadow-md overflow-hidden">
            <div class="px-6 py-5 border-b border-gray-200">
              <h3 class="text-lg font-medium leading-6 text-gray-900">알림 설정</h3>
            </div>
            <div class="px-6 py-5">
              <div class="space-y-6">
                <div class="relative flex items-start">
                  <div class="flex items-center h-5">
                    <input
                      id="email-notifications"
                      name="email-notifications"
                      type="checkbox"
                      v-model="userSettings.emailNotifications"
                      class="focus:ring-blue-500 h-4 w-4 text-blue-600 border-gray-300 rounded"
                    />
                  </div>
                  <div class="ml-3 text-sm">
                    <label for="email-notifications" class="font-medium text-gray-700"
                      >이메일 알림</label
                    >
                    <p class="text-gray-500">새로운 여행 정보와 추천을 이메일로 받습니다.</p>
                  </div>
                </div>

                <div class="relative flex items-start">
                  <div class="flex items-center h-5">
                    <input
                      id="push-notifications"
                      name="push-notifications"
                      type="checkbox"
                      v-model="userSettings.pushNotifications"
                      class="focus:ring-blue-500 h-4 w-4 text-blue-600 border-gray-300 rounded"
                    />
                  </div>
                  <div class="ml-3 text-sm">
                    <label for="push-notifications" class="font-medium text-gray-700"
                      >푸시 알림</label
                    >
                    <p class="text-gray-500">앱에서 푸시 알림을 받습니다.</p>
                  </div>
                </div>

                <div class="flex justify-end">
                  <button
                    type="submit"
                    @click="saveNotificationSettings"
                    class="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition-colors"
                  >
                    저장
                  </button>
                </div>
              </div>
            </div>
          </div>

          <div class="bg-white rounded-lg shadow-md overflow-hidden">
            <div class="px-6 py-5 border-b border-gray-200">
              <h3 class="text-lg font-medium leading-6 text-red-600">계정 삭제</h3>
            </div>
            <div class="px-6 py-5">
              <div class="mt-2 max-w-xl text-sm text-gray-500">
                <p>
                  계정을 삭제하면 모든 데이터가 영구적으로 삭제됩니다. 이 작업은 되돌릴 수 없습니다.
                </p>
              </div>
              <div class="mt-5">
                <button
                  type="button"
                  @click="confirmDeleteAccount"
                  class="inline-flex items-center justify-center px-4 py-2 border border-transparent font-medium rounded-md text-red-700 bg-red-100 hover:bg-red-200 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 sm:text-sm transition-colors"
                >
                  계정 삭제
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import HeaderNavBar from '@/components/HeaderNavBar.vue'
import Modal from '@/components/PlanModal.vue'
import axios from 'axios'
import { useUserStore } from '@/stores/user'

const showModal = ref(false)

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

const router = useRouter()
const activeTab = ref('overview')
const userStore = useUserStore()
const user = computed(() => userStore.user) // 또는 그냥 userStore.user
const route = useRoute()

// 사용자 설정
const userSettings = ref({
  name: '',
  email: '',
  newPassword: '',
  emailNotifications: true,
  pushNotifications: false,
})

// 여행 목록
onMounted(async () => {
  await userStore.fetchUserInfo() // 사용자 데이터 가져오기

  // 사용자 설정 초기화
  console.log('사용자 데이터:', user)
  userSettings.value.name = user.value.username || ''
  userSettings.value.email = user.value.email || ''
  if (route.query.activeTab) {
    activeTab.value = route.query.activeTab
  }
})

// 날짜 포맷팅
const formatDate = (dateString) => {
  if (!dateString) return '2023년 5월 15일' // 기본값

  const date = new Date(dateString)
  return `${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일`
}

// 새 여행 계획 페이지로 이동
const goToNewTrip = () => {
  showModal.value = true
}

// 게시판으로 이동
const goToBoard = () => {
  router.push('/Board')
}

// 설정 저장
const saveSettings = async () => {
  try {
    const token = localStorage.getItem('jwt')
    if (token) {
      const updateData = {
        username: userSettings.value.name,
        email: userSettings.value.email,
      }

      // 비밀번호가 입력된 경우에만 포함
      if (userSettings.value.newPassword) {
        if (userSettings.value.newPassword.length < 8) {
          alert('비밀번호는 최소 8자 이상이어야 합니다.')
          return
        }
        updateData.password = userSettings.value.newPassword
      }

      await axios.put('/api/user/update', updateData, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })

      await userStore.fetchUserInfo() // 사용자 정보 새로고침
      alert('설정이 저장되었습니다.')

      // 비밀번호 필드 초기화
      userSettings.value.newPassword = ''
    }
  } catch (error) {
    console.error('설정 저장 실패:', error)
    alert('설정 저장에 실패했습니다.')
  }
}

// 알림 설정 저장
const saveNotificationSettings = () => {
  // 알림 설정 저장 로직 (로컬 스토리지 또는 API)
  localStorage.setItem('emailNotifications', userSettings.value.emailNotifications)
  localStorage.setItem('pushNotifications', userSettings.value.pushNotifications)
  alert('알림 설정이 저장되었습니다.')
}

// 계정 삭제 확인 및 API 호출
const confirmDeleteAccount = async () => {
  if (confirm('정말로 계정을 삭제하시겠습니까? 이 작업은 되돌릴 수 없습니다.')) {
    try {
      const token = localStorage.getItem('jwt')

      // ✅ 계정 삭제 요청 (DELETE 메서드 사용)
      await axios.delete('/api/user/delete', {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })

      alert('계정이 성공적으로 삭제되었습니다.')

      // 로컬 스토리지 정리 및 로그아웃 처리
      localStorage.removeItem('jwt')
      localStorage.removeItem('email')
      userStore.logout() //pinia 상태 삭제

      // 로그인 페이지로 이동
      router.push('/login')
    } catch (error) {
      console.error('계정 삭제 실패:', error)
      alert('계정 삭제 중 오류가 발생했습니다.')
    }
  }
}

// 상태 텍스트 반환
const getStatusText = (status) => {
  switch (status) {
    case 'completed':
      return '완료됨'
    case 'ongoing':
      return '진행중'
    case 'planned':
      return '계획됨'
    default:
      return '알 수 없음'
  }
}

// 여행 보기
const viewTrip = (schedule) => {
  // 여행 상세 페이지로 이동하거나 모달 표시
  alert(`"${schedule.title}" 여행을 확인합니다.`)
  router.push({ name: 'scheduleDetail', params: { scheduleId: schedule.scheduleId } })
}

// 여행 수정
const editTrip = async (trip) => {
  // 여행 수정 페이지로 이동
  alert(`"${trip.title}" 여행을 수정합니다.`)

  const token = localStorage.getItem('jwt')
  try {
    const response = await axios.get(`/api/user/schedule/${trip.scheduleId}`, {
      headers: { Authorization: `Bearer ${token}` },
    })

    const fullTripData = response.data

    localStorage.setItem('editTripData', JSON.stringify(fullTripData))

    router.push({
      name: 'editSchedule',
      params: { scheduleId: trip.scheduleId },
    })
  } catch (error) {
    console.error('여행 수정 데이터 가져오기 실패:', error)
    alert('여행 수정 데이터를 가져오는 데 실패했습니다.')
  }
}

// 여행 삭제 확인
const confirmDeleteTrip = (schedule) => {
  if (confirm(`"${schedule.title}" 여행을 정말 삭제하시겠습니까?`)) {
    deleteTrip(schedule.scheduleId)
  }
}

// 여행 삭제
const deleteTrip = async (scheduleId) => {
  const token = localStorage.getItem('jwt')
  if (!token) {
    alert('로그인이 필요합니다.')
    return
  }

  try {
    await axios.delete(`/api/user/schedule/${scheduleId}`, {
      headers: { Authorization: `Bearer ${token}` },
    })
  } catch (error) {
    console.error('여행 삭제 실패:', error)
    alert('여행 삭제에 실패했습니다.')
    return
  }

  await userStore.deleteSchedule(scheduleId)
  alert('여행이 삭제되었습니다.')
  //window.location.href = '/mypage'
}
</script>

<style scoped>
/* 트랜지션 효과 */
.transition-colors {
  transition-property: background-color, border-color, color, fill, stroke;
  transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
  transition-duration: 150ms;
}

.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
