<!-- src/components/LocationSelection.vue -->
<template>
  <div class="flex h-screen">
    <!-- 마이페이지 드롭다운 -->
    <div class="absolute top-4 right-4 z-30">
      <div class="relative">
        <button
          @click="showUserDropdown = !showUserDropdown"
          class="flex items-center justify-center w-10 h-10 bg-white rounded-full shadow-md hover:shadow-lg transition-shadow duration-300"
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
    </div>
    <!-- ─── 1번 사이드바: 로고 · 내비 · 다음 버튼 ─── -->
    <aside class="w-24 flex flex-col bg-gray-50 border-r border-gray-200">
      <!-- 로고 -->
      <div class="h-20 flex items-center justify-center">
        <img src="@/assets/logo.png" alt="WIT Logo" @click="goHome" class="cursor-pointer" />
      </div>

      <!-- 내비 -->
      <nav class="flex-1 flex flex-col items-center space-y-16 mt-12">
        <button
          @click="activeNav = 'places'"
          :class="[
            'transform transition-all duration-250 ease-in-out',
            activeNav === 'places'
              ? 'text-blue-600 text-lg scale-90 font-semibold'
              : 'text-gray-400 text-sm scale-90',
          ]"
          title="장소 선택"
        >
          🔍
          <br />
          장소 선택
        </button>

        <button
          @click="activeNav = 'accommodation'"
          :class="[
            'transform transition-all duration-250 ease-in-out',
            activeNav === 'accommodation'
              ? 'text-blue-600 text-lg scale-90 font-semibold'
              : 'text-gray-400 text-sm scale-90',
          ]"
          title="숙소 설정"
        >
          🏠
          <br />
          숙소 설정
        </button>

        <button
          @click="activeNav = 'plan'"
          :class="[
            'transform transition-all duration-250 ease-in-out',
            activeNav === 'plan'
              ? 'text-blue-600 text-lg scale-90 font-semibold'
              : 'text-gray-400 text-sm scale-90',
          ]"
          title="일정 생성"
        >
          🗓️
          <br />
          일정 생성
        </button>
      </nav>

      <!-- 다음 버튼 (맨 아래) -->
      <div class="p-4">
        <button
          @click="goNext"
          class="w-full bg-blue-600 text-white py-2 rounded-lg shadow"
          :title="buttonLabel"
        >
          {{ buttonLabel }}
        </button>
      </div>
    </aside>
    <!--2-1-->
    <!-- ─── 2번 사이드바: 장소 선택 화면 ─── -->
    <aside class="w-[26rem] flex flex-col bg-gray-50 border-r border-gray-200 z-20">
      <div class="p-6 flex-1 flex flex-col" v-if="activeNav === 'places'">
        <h2
          class="text-center text-lg font-semibold text-blue-600 border-b-2 border-blue-600 pb-2 mb-4"
        >
          장소 선택
        </h2>

        <!-- ─── 필터 & 탭 영역 (업데이트) ─── -->
        <div class="mb-6">
          <!-- 1행: 지역 + 검색 -->
          <div class="flex mb-4">
            <!-- 여기에 mb-4 추가 -->
            <div
              class="flex flex-1 items-center bg-white border border-gray-300 rounded-lg shadow-md h-12"
            >
              <!-- 지역 선택 -->
              <select
                v-model="selectedRegion"
                class="px-4 py-2 text-sm bg-transparent text-gray-700 focus:outline-none"
              >
                <option v-for="sido in sidos" :key="sido.sido_code" :value="sido.sido_code">
                  {{ sido.sido_name }}
                </option>
              </select>

              <!-- 구분선 (간격 유지) -->
              <div class="w-px h-6 mx-2 bg-gray-300"></div>

              <!-- 검색 입력 -->
              <input
                v-model="searchQuery"
                type="text"
                placeholder="장소명을 입력하세요."
                class="flex-1 px-4 py-2 text-sm bg-transparent placeholder-gray-400 focus:outline-none"
              />
            </div>
          </div>

          <!-- 2행: 탭 (왼쪽) + 일차 선택 (오른쪽) -->
          <div class="flex items-center justify-between mt-6">
            <!-- mt-2 → mt-6 -->
            <!-- 탭 버튼 -->
            <div class="flex space-x-2">
              <button
                v-for="tab in tabs"
                :key="tab"
                @click="activeTab = tab"
                :class="[
                  'px-2 py-1 rounded-md text-xs font-medium border',
                  activeTab === tab
                    ? 'bg-blue-600 text-white border-blue-600'
                    : 'bg-white text-gray-600 border-gray-300',
                ]"
              >
                {{ tab }}
              </button>
            </div>

            <!-- 일차 선택 -->
            <!-- wrapper에 relative, select에 appearance-none -->
            <div class="relative inline-block">
              <select
                v-model="placeDay"
                class="appearance-none w-auto pl-2 pr-8 py-1 text-xs border border-gray-300 rounded-md bg-white text-gray-700 focus:outline-none"
              >
                <option v-for="opt in dayOptions" :key="opt.value" :value="opt.value">
                  {{ opt.label }}
                </option>
              </select>
              <!-- 절대위치 SVG: right-2 만큼 테두리와 거리 확보 -->
              <svg
                class="pointer-events-none absolute right-2 top-1/2 transform -translate-y-1/2 w-4 h-4 text-gray-600"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M19 9l-7 7-7-7"
                />
              </svg>
            </div>
          </div>
        </div>

        <!-- 리스트 -->
        <!-- 전체 리스트 박스를 고정 높이로 제한 -->
        <div class="overflow-y-auto max-h-[43rem] px-1">
          <ul class="space-y-3">
            <li
              v-for="p in places"
              :key="p.id"
              class="flex items-center bg-white rounded-lg p-3 shadow-sm cursor-pointer hover:shadow-md transition-shadow"
              @click="showPlaceDetailModal(p)"
            >
              <img
                :src="p.firstImage1 ? p.firstImage1 : getDefaultImage(p.contentTypeId)"
                alt="이미지"
                class="w-16 h-16 rounded object-cover mr-4"
              />
              <div class="flex-1 min-w-0">
                <div class="flex items-center space-x-2">
                  <span class="font-semibold truncate">{{ p.title }}</span>
                </div>
                <div class="flex-1">
                  <p
                    class="text-sm text-blue-500"
                    :class="{
                      'text-pink-500': p.contentTypeId === 1 || p.contentTypeId === 2,
                    }"
                  >
                    {{ getContentTypeName(p.contentTypeId) }}
                  </p>
                </div>
                <div class="flex items-center space-x-2 text-xs text-gray-400 mt-1">
                  <!-- 별점 UI -->
                  <div class="flex items-center space-x-0.5">
                    <template v-for="i in 5">
                      <!-- 꽉 찬 별 -->
                      <svg
                        v-if="p.rating >= i"
                        :key="i + '-full'"
                        class="w-4 h-4 text-yellow-400"
                        fill="currentColor"
                        viewBox="0 0 20 20"
                      >
                        <path
                          d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.286 3.955h4.162c.969 0 1.371 1.24.588 1.81l-3.37 2.448 1.286 3.954c.3.922-.755 1.688-1.539 1.118L10 13.348l-3.374 2.866c-.784.57-1.838-.196-1.539-1.118l1.286-3.954-3.37-2.448c-.783-.57-.38-1.81.588-1.81h4.162l1.286-3.955z"
                        />
                      </svg>

                      <!-- 반 별 -->
                      <svg
                        v-else-if="p.rating >= i - 0.5"
                        :key="i + '-half'"
                        class="w-4 h-4 text-yellow-400"
                        fill="currentColor"
                        viewBox="0 0 20 20"
                      >
                        <path
                          d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.286 3.955h4.162c.969 0 1.371 1.24.588 1.81l-3.37 2.448 1.286 3.954c.3.922-.755 1.688-1.539 1.118L10 13.348l-3.374 2.866c-.784.57-1.838-.196-1.539-1.118l1.286-3.954-3.37-2.448c-.783-.57-.38-1.81.588-1.81h4.162l1.286-3.955z"
                          fill="url(#halfGrad)"
                        />
                        <defs>
                          <linearGradient id="halfGrad">
                            <stop offset="50%" stop-color="currentColor" />
                            <stop offset="50%" stop-color="lightgray" />
                          </linearGradient>
                        </defs>
                      </svg>

                      <!-- 빈 별 -->
                      <svg
                        v-else
                        :key="i + '-empty'"
                        class="w-4 h-4 text-gray-300"
                        fill="currentColor"
                        viewBox="0 0 20 20"
                      >
                        <path
                          d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.286 3.955h4.162c.969 0 1.371 1.24.588 1.81l-3.37 2.448 1.286 3.954c.3.922-.755 1.688-1.539 1.118L10 13.348l-3.374 2.866c-.784.57-1.838-.196-1.539-1.118l1.286-3.954-3.37-2.448c-.783-.57-.38-1.81.588-1.81h4.162l1.286-3.955z"
                        />
                      </svg>
                    </template>

                    <!-- 숫자 표시 -->
                    <span class="ml-1 text-xs text-gray-500">{{ p.rating }}</span>
                  </div>
                </div>
              </div>
              <button
                @click.stop="addPlace(p)"
                class="w-8 h-8 flex-shrink-0 flex items-center justify-center border rounded text-gray-400"
              >
                ＋
              </button>
            </li>
          </ul>
          <!-- 더보기 버튼 -->
          <div class="text-center py-4" v-if="hasMore">
            <button
              @click="loadMore"
              :disabled="loadingMore"
              class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition-colors"
            >
              {{ loadingMore ? '로딩 중…' : '더보기' }}
            </button>
          </div>
        </div>
      </div>

      <!--2-2-->
      <!-- 숙소 설정 화면 추가 -->
      <div class="p-6 flex-1 flex flex-col" v-if="activeNav === 'accommodation'">
        <h2
          class="text-center text-lg font-semibold text-blue-600 border-b-2 border-blue-600 pb-2 mb-4"
        >
          숙소 설정
        </h2>

        <!-- 일차 선택 드롭다운 -->
        <div class="mb-4">
          <div class="relative inline-block w-full">
            <select
              v-model="accommodationDay"
              class="appearance-none w-full pl-3 pr-8 py-2 text-sm border border-gray-300 rounded-md bg-white focus:outline-none focus:ring-2 focus:ring-blue-600"
            >
              <option v-for="d in planDayOptions" :key="d" :value="d">{{ d }}일차 숙소 설정</option>
            </select>
            <svg
              class="pointer-events-none absolute right-2 top-1/2 transform -translate-y-1/2 w-4 h-4 text-gray-600"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M19 9l-7 7-7-7"
              />
            </svg>
          </div>
        </div>

        <!-- 검색 영역 -->
        <div class="mb-4">
          <div
            class="flex flex-1 items-center bg-white border border-gray-300 rounded-lg shadow-md h-12"
          >
            <input
              v-model="accommodationSearchQuery"
              type="text"
              placeholder="숙소명 또는 주소를 검색하세요"
              class="flex-1 px-4 py-2 text-sm bg-transparent placeholder-gray-400 focus:outline-none"
              @keyup.enter="searchAccommodation"
            />
            <button
              @click="searchAccommodation"
              class="px-4 h-full text-gray-600 hover:text-blue-600 transition-colors"
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
                  d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
                />
              </svg>
            </button>
          </div>
        </div>

        <!-- 작은 지도 -->
        <div class="mb-4 bg-gray-100 rounded-lg shadow-md overflow-hidden">
          <div ref="accommodationMapContainer" class="w-full h-48"></div>
        </div>

        <!-- 검색 결과 -->
        <div class="mb-4">
          <div class="flex items-center justify-between mb-2">
            <h3 class="text-sm font-medium text-gray-700">검색 결과</h3>
            <span class="text-xs text-gray-500"
              >{{ accommodationSearchResults.length }}개 결과</span
            >
          </div>
          <div class="overflow-y-auto max-h-[12rem] bg-gray-100 rounded-lg p-4 shadow-md">
            <div v-if="accommodationSearchResults.length > 0">
              <div
                v-for="(result, index) in accommodationSearchResults"
                :key="index"
                class="bg-white rounded-lg shadow-sm p-3 mb-3"
              >
                <div class="flex items-center justify-between">
                  <div class="flex-1">
                    <!-- 장소명이 있는 경우 -->
                    <div v-if="result.place_name">
                      <p class="font-medium text-gray-900">{{ result.place_name }}</p>
                      <p class="text-xs text-gray-500">{{ result.address_name }}</p>
                      <p v-if="result.phone" class="text-xs text-gray-500">{{ result.phone }}</p>
                    </div>
                    <!-- 주소만 있는 경우 -->
                    <div v-else>
                      <p class="font-medium text-gray-900">{{ result.address_name }}</p>
                      <div v-if="selectedAddressIndex === index" class="mt-2">
                        <input
                          v-model="customPlaceName"
                          type="text"
                          placeholder="장소명을 입력하세요"
                          class="w-full px-3 py-1 text-sm border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-600"
                          @keyup.enter="addCustomAccommodation(result)"
                        />
                      </div>
                    </div>
                  </div>
                  <div class="flex items-center space-x-2">
                    <!-- 장소명이 있는 경우 바로 추가 버튼 -->
                    <button
                      v-if="result.place_name"
                      @click="addAccommodation(result)"
                      class="flex-shrink-0 flex items-center justify-center px-3 py-1 bg-blue-600 text-white text-sm rounded-md hover:bg-blue-700 transition-colors"
                    >
                      등록
                    </button>
                    <!-- 주소만 있는 경우 정보 입력 버튼 -->
                    <button
                      v-else
                      @click="selectAddressForInput(index)"
                      class="flex-shrink-0 flex items-center justify-center px-3 py-1 bg-gray-600 text-white text-sm rounded-md hover:bg-gray-700 transition-colors"
                    >
                      {{ selectedAddressIndex === index ? '등록' : '정보 입력' }}
                    </button>
                  </div>
                </div>
              </div>
            </div>
            <div v-else class="flex items-center justify-center h-32 text-gray-500">
              검색 결과가 없습니다
            </div>
          </div>
        </div>

        <!-- 등록된 숙소 목록 -->
        <div class="mb-4">
          <div class="flex items-center justify-between mb-2">
            <h3 class="text-sm font-medium text-gray-700">등록된 숙소</h3>
            <button
              @click="accommodations = []"
              class="text-xs text-red-500 hover:underline"
              v-if="accommodations.length > 0"
            >
              초기화
            </button>
          </div>
          <div class="overflow-y-auto max-h-[12rem] bg-gray-100 rounded-lg p-4 shadow-md">
            <div v-if="accommodations.length > 0">
              <div
                v-for="(accommodation, index) in accommodations"
                :key="index"
                class="bg-white rounded-lg shadow-sm p-3 mb-3"
              >
                <div class="flex items-center justify-between">
                  <div class="flex-1">
                    <p class="font-medium text-gray-900">{{ accommodation.name }}</p>
                    <p class="text-xs text-gray-500">{{ accommodation.address }}</p>
                  </div>
                  <div class="flex items-center space-x-2">
                    <div class="flex">
                      <button
                        @click="setAsStartLocation(index)"
                        class="flex-shrink-0 flex items-center justify-center px-2 py-1 text-xs bg-green-600 text-white rounded-l-md hover:bg-green-700 transition-colors"
                        title="시작 장소로 설정"
                      >
                        시작
                      </button>
                      <button
                        @click="setAsEndLocation(index)"
                        class="flex-shrink-0 flex items-center justify-center px-2 py-1 text-xs bg-red-600 text-white rounded-r-md hover:bg-red-700 transition-colors"
                        title="종료 장소로 설정"
                      >
                        종료
                      </button>
                    </div>
                    <button
                      @click="removeAccommodation(index)"
                      class="w-8 h-8 flex-shrink-0 flex items-center justify-center border rounded text-gray-400 hover:text-red-600 hover:border-red-600"
                    >
                      ✕
                    </button>
                  </div>
                </div>
              </div>
            </div>
            <div v-else class="flex items-center justify-center h-32 text-gray-500">
              등록된 숙소가 없습니다
            </div>
          </div>
        </div>
      </div>

      <!-- 일정 생성 -->
      <div v-else-if="activeNav === 'plan'" class="p-6 flex flex-col flex-1">
        <!-- 헤더: 중앙 제목 + 오른쪽 드롭다운, 전체 너비에 밑줄 적용 -->
        <div class="relative mb-4 border-b-2 border-blue-600">
          <h2 class="text-center text-lg font-semibold text-blue-600 pb-2">일정 생성</h2>
          <div class="absolute right-0 top-0">
            <div class="relative inline-block w-24">
              <select
                v-model="planDay"
                class="appearance-none w-full pl-3 pr-8 py-1 text-sm border border-gray-300 rounded-md bg-white focus:outline-none"
              >
                <option v-for="d in planDayOptions" :key="d" :value="d">{{ d }}일차</option>
              </select>
              <svg
                class="pointer-events-none absolute right-2 top-1/2 transform -translate-y-1/2 w-4 h-4 text-gray-600"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M19 9l-7 7-7-7"
                />
              </svg>
            </div>
          </div>
        </div>

        <!-- “시작 시간 입력” 부분 -->
        <div class="mb-4">
          <div
            class="flex items-center justify-center bg-white border border-gray-300 rounded-lg shadow-md h-12 px-4 space-x-3"
          >
            <!-- 레이블 -->
            <span class="text-gray-900 select-none">시작</span>

            <!-- 수정: w-auto + min-w-[5ch] 로 크기 자동 + 최소 5글자 -->
            <input
              type="time"
              v-model="startTimeRaw"
              class="w-auto min-w-[5ch] w-[113px] bg-transparent border-none focus:outline-none text-gray-900 font-semibold text-center"
            />
          </div>
        </div>

        <!-- 자차 / 대중교통 + 최적 경로 정렬 버튼을 한 줄에, 같은 높이로 -->
        <div class="mb-4 flex items-center justify-between space-x-4">
          <!-- 자차 / 대중교통 탭 -->
          <div class="flex flex-1 h-10 rounded-md overflow-hidden border border-gray-300 shadow-md">
            <button
              @click="transportModeValue = 'car'"
              :class="[
                'flex-1 text-sm transition-colors',
                transportModeValue === 'car'
                  ? 'bg-blue-600 text-white font-medium'
                  : 'bg-white text-gray-700 hover:bg-gray-50',
              ]"
            >
              자차
            </button>
            <button
              @click="transportModeValue = 'public'"
              :class="[
                'flex-1 text-sm transition-colors',
                transportModeValue === 'public'
                  ? 'bg-blue-600 text-white font-medium'
                  : 'bg-white text-gray-700 hover:bg-gray-50',
              ]"
            >
              대중교통
            </button>
          </div>

          <!-- 최적 경로 버튼 -->
          <button
            @click="optimizeRouteHandler"
            class="h-10 px-4 flex items-center bg-blue-600 text-white text-sm font-medium rounded-md hover:bg-blue-700 transition-colors shadow whitespace-nowrap"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="w-4 h-4 mr-2"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6"
              />
            </svg>
            최적 경로로 정렬
          </button>
        </div>

        <!-- draggable 리스트 컨테이너 -->
        <div
          class="overflow-y-auto max-h-[43rem] bg-gray-100 rounded-lg p-4 shadow-md scroll smooth"
        >
          <!-- (앞에) 시작 장소 표시 -->
          <div
            v-if="
              dayAccommodations[planDay]?.[0].index !== -1 &&
              accommodations[dayAccommodations[planDay][0].index]
            "
            class="plan-item bg-white h-[5rem] rounded-lg shadow flex items-center justify-between px-4 mb-2 mr-2"
          >
            <span
              class="flex items-center justify-center w-10 h-6 rounded-full bg-green-500 text-white text-xs font-semibold"
            >
              출발
            </span>
            <div class="flex-1 flex flex-col justify-evenly h-full ml-3">
              <!--숙소 이름-->
              <span class="block text-sm font-semibold text-gray-900">
                {{ accommodations[dayAccommodations[planDay][0].index].name }}
              </span>
              <!-- 카테고리 + 시간 -->
              <span class="block text-sm text-blue-600">
                숙소 {{ dayAccommodations[planDay][0].time }}
              </span>

              <!-- 머무는 시간 입력부 -->
              <div class="flex items-center text-sm text-gray-400 space-x-1">
                <!-- 레이블 -->
                <span class="select-none mr-0.5">머무는 시간</span>

                <!-- 시간 입력 + 단위 -->
                <div class="flex items-center space-x-0.5">
                  <input
                    type="number"
                    v-model.number="dayAccommodations[planDay][0].stayHour"
                    min="0"
                    max="24"
                    @input="
                      ((dayAccommodations[planDay][0].stayHour = Math.min(
                        Math.max(dayAccommodations[planDay][0].stayHour, 0),
                        24,
                      )),
                      recalcSchedule())
                    "
                    class="w-[26px] h-5 bg-transparent border border-transparent focus:border-gray-300 focus:ring-1 focus:ring-gray-300 rounded text-center leading-tight p-0"
                  />
                  <span class="select-none">시간</span>
                </div>

                <!-- 분 입력 + 단위 -->
                <div class="flex items-center space-x-0.5">
                  <input
                    type="number"
                    v-model.number="dayAccommodations[planDay][0].stayMin"
                    min="0"
                    max="59"
                    @input="
                      ((dayAccommodations[planDay][0].stayMin = Math.min(
                        Math.max(dayAccommodations[planDay][0].stayMin, 0),
                        59,
                      )),
                      recalcSchedule())
                    "
                    class="w-[26px] h-5 bg-transparent border border-transparent focus:border-gray-300 focus:ring-1 focus:ring-gray-300 rounded text-center leading-tight p-0"
                  />
                  <span class="select-none">분</span>
                </div>
              </div>
            </div>
            <!-- 3) 우측 아이콘 -->
            <div class="flex items-center space-x-2">
              <!-- 삭제 버튼 -->
              <button
                @click="clearStartLocation"
                class="p-2 flex items-center justify-center rounded-md hover:bg-gray-100 transition-colors"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="w-5 h-5 text-gray-400 cursor-pointer"
                  viewBox="0 0 20 20"
                  fill="currentColor"
                >
                  +
                  <path
                    fill-rule="evenodd"
                    d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 011.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                    clip-rule="evenodd"
                  />
                </svg>
              </button>
            </div>
          </div>
          <!-- 시작 장소 밑 이동 시간 -->
          <div
            v-if="
              dayAccommodations[planDay] &&
              dayAccommodations[planDay]?.[0].index !== -1 &&
              selectedPlacesByDay[planDay].length !== 0
            "
            :class="[
              'travel-block flex items-center justify-center space-x-2 bg-transparent',
              {
                invisible: isDragging,
              },
            ]"
          >
            <div class="border-l-2 border-dashed border-gray-400 h-6"></div>
            <span class="text-sm text-gray-700">이동 시간</span>
            <input
              v-if="transportModeValue === 'car'"
              type="number"
              v-model.number="dayAccommodations[planDay][0].travelCar"
              min="0"
              @input="
                ((dayAccommodations[planDay][0].travelCar = Math.max(
                  dayAccommodations[planDay][0].travelCar,
                  0,
                )),
                recalcSchedule())
              "
              class="w-14 h-8 bg-white border border-gray-300 rounded-md px-2 text-center text-base font-semibold focus:outline-none focus:ring-1 focus:ring-blue-200"
            />
            <input
              v-else
              type="number"
              v-model.number="dayAccommodations[planDay][0].travelBus"
              min="0"
              @input="
                ((dayAccommodations[planDay][0].travelBus = Math.max(
                  dayAccommodations[planDay][0].travelBus,
                  0,
                )),
                recalcSchedule())
              "
              class="w-14 h-8 bg-white border border-gray-300 rounded-md px-2 text-center text-base font-semibold focus:outline-none focus:ring-1 focus:ring-blue-200"
            />
            <span class="text-sm text-gray-700">분</span>
          </div>
          <draggable
            v-model="selectedPlacesByDay[planDay]"
            handle=".drag-handle"
            :group="{ name: 'places', pull: true, put: true }"
            :animation="200"
            @add="onPlanAdd"
            :force-fallback="true"
            :fallback-on-body="true"
            item-key="instanceId"
            @start="onDragStart"
            @end="onDragEnd"
            drag-class="drag-no-travel"
            :class="['space-y-2 pr-2', { 'is-dragging': isDragging }]"
            :scroll-sensitivity="80"
            :scroll-speed="15"
            :move="
              ({ draggedContext }) => {
                const el = draggedContext.element
                // isStart/isEnd 아이템은 드래그 불가
                return !el.isStart && !el.isEnd
              }
            "
          >
            <template #item="{ element: item, index }">
              <div class="space-y-1">
                <!-- plan-item container -->
                <div
                  class="plan-item drag-handle cursor-move bg-white h-[5rem] rounded-lg shadow flex items-center justify-between px-4"
                >
                  <!-- 1) 좌측 숫자 -->
                  <span
                    class="flex items-center justify-center w-6 h-6 rounded-full bg-red-500 text-white text-xs font-semibold"
                  >
                    {{ index + 1 }}
                  </span>

                  <!-- plan-item 중앙 콘텐츠 부분 -->
                  <div class="flex-1 flex flex-col justify-evenly h-full ml-3">
                    <!-- 장소명 -->
                    <span class="block text-sm font-semibold text-gray-900">{{ item.title }}</span>

                    <!-- 카테고리 + 시간 -->
                    <span class="block text-sm text-blue-600">
                      {{ getContentTypeName(item.contentTypeId) }} {{ item.time }}
                    </span>

                    <!-- 머무는 시간 입력부 -->
                    <div class="flex items-center text-sm text-gray-400 space-x-1">
                      <!-- 레이블 -->
                      <span class="select-none mr-0.5">머무는 시간</span>

                      <!-- 시간 입력 + 단위 -->
                      <div class="flex items-center space-x-0.5">
                        <input
                          type="number"
                          v-model.number="item.stayHour"
                          min="0"
                          max="24"
                          @input="
                            ((item.stayHour = Math.min(Math.max(item.stayHour, 0), 24)),
                            recalcSchedule())
                          "
                          class="w-[26px] h-5 bg-transparent border border-transparent focus:border-gray-300 focus:ring-1 focus:ring-gray-300 rounded text-center leading-tight p-0"
                        />
                        <span class="select-none">시간</span>
                      </div>

                      <!-- 분 입력 + 단위 -->
                      <div class="flex items-center space-x-0.5">
                        <input
                          type="number"
                          v-model.number="item.stayMin"
                          min="0"
                          max="59"
                          @input="
                            ((item.stayMin = Math.min(Math.max(item.stayMin, 0), 59)),
                            recalcSchedule())
                          "
                          class="w-[26px] h-5 bg-transparent border border-transparent focus:border-gray-300 focus:ring-1 focus:ring-gray-300 rounded text-center leading-tight p-0"
                        />
                        <span class="select-none">분</span>
                      </div>
                    </div>
                  </div>

                  <!-- 3) 우측 아이콘 -->
                  <div class="flex items-center space-x-2">
                    <!-- drag handle 아이콘 -->
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      class="w-5 h-5 text-gray-400 translate-y-[1px]"
                      fill="currentColor"
                      viewBox="0 0 20 20"
                    >
                      <path
                        d="M7 4a1 1 0 100 2 1 1 0 000-2zm0 4a1 1 0 100 2 1 1 0 000-2zm0 4a1 1 0 100 2 1 1 0 000-2zm6-8a1 1 0 100 2 1 1 0 000-2zm0 4a1 1 0 100 2 1 1 0 000-2zm0 4a1 1 0 100 2 1 1 0 000-2z"
                      />
                    </svg>
                    <!-- 삭제 버튼 -->
                    <button
                      @click="removeSelectedPlace(index, planDay)"
                      class="p-2 flex items-center justify-center rounded-md hover:bg-gray-100 transition-colors"
                    >
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        class="w-5 h-5 text-gray-400 cursor-pointer"
                        viewBox="0 0 20 20"
                        fill="currentColor"
                      >
                        +
                        <path
                          fill-rule="evenodd"
                          d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 011.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                          clip-rule="evenodd"
                        />
                      </svg>
                    </button>
                  </div>
                </div>

                <!-- 2) 카드 사이사이에 들어갈 이동 시간 -->
                <!-- travel-block -->
                <div
                  :class="[
                    'travel-block flex items-center justify-center space-x-2 bg-transparent',
                    {
                      invisible:
                        isDragging ||
                        (index >= selectedPlacesByDay[planDay].length - 1 &&
                          dayAccommodations[planDay]?.[1].index === -1),
                    },
                  ]"
                >
                  <!-- 스트라이프 -->
                  <div class="border-l-2 border-dashed border-gray-400 h-6"></div>

                  <!-- 레이블 -->
                  <span class="text-sm text-gray-700">이동 시간</span>

                  <!-- 입력 박스 -->
                  <input
                    v-if="transportModeValue === 'car'"
                    type="number"
                    v-model.number="selectedPlacesByDay[planDay][index].travelCar"
                    min="0"
                    @input="((item.travelCar = Math.max(item.travelCar, 0)), recalcSchedule())"
                    class="w-14 h-8 bg-white border border-gray-300 rounded-md px-2 text-center text-base font-semibold focus:outline-none focus:ring-1 focus:ring-blue-200"
                  />
                  <!-- 대중교통 모드일 때 -->
                  <input
                    v-else
                    type="number"
                    v-model.number="selectedPlacesByDay[planDay][index].travelBus"
                    min="0"
                    @input="((item.travelBus = Math.max(item.travelBus, 0)), recalcSchedule())"
                    class="w-14 h-8 bg-white border border-gray-300 rounded-md px-2 text-center text-base font-semibold focus:outline-none focus:ring-1 focus:ring-blue-200"
                  />

                  <!-- 단위 -->
                  <span class="text-sm text-gray-700">분</span>
                </div>
              </div>
            </template>
            <!-- 2) footer 슬롯에 빈 상태 메시지 -->
            <template #footer>
              <div
                class="flex items-center justify-center text-gray-400 italic h-64 w-full select-none"
                v-if="selectedPlacesByDay[planDay].length === 0"
              >
                일정이 없습니다.
              </div>
            </template>
          </draggable>

          <!-- (뒤에) 종료 장소 표시 -->
          <div
            v-if="
              dayAccommodations[planDay] &&
              dayAccommodations[planDay]?.[1].index !== -1 &&
              accommodations[dayAccommodations[planDay][1].index]
            "
            class="plan-item bg-white h-[5rem] rounded-lg shadow flex items-center justify-between px-4 mt-2 mr-2"
          >
            <span
              class="flex items-center justify-center w-10 h-6 rounded-full bg-red-500 text-white text-xs font-semibold"
            >
              도착
            </span>
            <div class="flex-1 flex flex-col justify-evenly h-full ml-3">
              <!--숙소 이름-->
              <span class="block text-sm font-semibold text-gray-900">
                {{ accommodations[dayAccommodations[planDay][1].index].name }}
              </span>
              <!-- 카테고리 + 시간 -->
              <span class="block text-sm text-blue-600">
                숙소 {{ dayAccommodations[planDay][1].time }}
              </span>

              <!-- 머무는 시간 입력부 -->
              <div class="flex items-center text-sm text-gray-400 space-x-1">
                <!-- 레이블 -->
                <span class="select-none mr-0.5">머무는 시간</span>

                <!-- 시간 입력 + 단위 -->
                <div class="flex items-center space-x-0.5">
                  <input
                    type="number"
                    v-model.number="dayAccommodations[planDay][1].stayHour"
                    min="0"
                    max="24"
                    @input="
                      ((dayAccommodations[planDay][1].stayHour = Math.min(
                        Math.max(dayAccommodations[planDay][1].stayHour, 0),
                        24,
                      )),
                      recalcSchedule())
                    "
                    class="w-[26px] h-5 bg-transparent border border-transparent focus:border-gray-300 focus:ring-1 focus:ring-gray-300 rounded text-center leading-tight p-0"
                  />
                  <span class="select-none">시간</span>
                </div>

                <!-- 분 입력 + 단위 -->
                <div class="flex items-center space-x-0.5">
                  <input
                    type="number"
                    v-model.number="dayAccommodations[planDay][1].stayMin"
                    min="0"
                    max="59"
                    @input="
                      ((dayAccommodations[planDay][1].stayMin = Math.min(
                        Math.max(dayAccommodations[planDay][1].stayMin, 0),
                        59,
                      )),
                      recalcSchedule())
                    "
                    class="w-[26px] h-5 bg-transparent border border-transparent focus:border-gray-300 focus:ring-1 focus:ring-gray-300 rounded text-center leading-tight p-0"
                  />
                  <span class="select-none">분</span>
                </div>
              </div>
            </div>
            <!-- 3) 우측 아이콘 -->
            <div class="flex items-center space-x-2">
              <!-- 삭제 버튼 -->
              <button
                @click="clearEndLocation"
                class="p-2 flex items-center justify-center rounded-md hover:bg-gray-100 transition-colors"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="w-5 h-5 text-gray-400 cursor-pointer"
                  viewBox="0 0 20 20"
                  fill="currentColor"
                >
                  <path
                    fill-rule="evenodd"
                    d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 011.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                    clip-rule="evenodd"
                  />
                </svg>
              </button>
            </div>
          </div>
        </div>
      </div>
    </aside>

    <!--3번째 사이드바-->
    <!-- 3번: 지도 + 오버레이 사이드바 -->
    <div class="relative flex-1">
      <!-- 지도 -->
      <div ref="mapContainer" class="w-full h-full"></div>

      <!-- 토글 버튼 (2번 사이드바 바로 옆에 고정) -->
      <button
        @click="showDetailSidebar = !showDetailSidebar"
        class="absolute z-20 top-1/2 transform -translate-y-1/2 p-2 bg-white border rounded-full shadow cursor-pointer transition-all duration-300"
        :style="{ left: toggleBtnLeft }"
        :class="{ 'rotate-180': showDetailSidebar }"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="w-5 h-5 text-gray-600"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
        </svg>
      </button>
      <!-- 디테일 사이드바: left-0, -translate-x-full → translate-x-0 -->
      <aside
        class="absolute top-0 left-0 h-full w-[26rem] bg-gray-50 border-none z-10 transform transition-transform duration-300 shadow-none"
        :class="showDetailSidebar ? 'translate-x-0' : '-translate-x-full'"
      >
        <!--3-1 사이드바-->
        <div class="p-6 flex flex-col h-full" v-if="activeNav === 'places'">
          <div class="mt-6">
            <!-- 선택된 장소 개수 & 초기화 -->
            <div class="flex items-center justify-between mb-4">
              <span class="font-medium"
                >{{
                  placeDay === 'storage'
                    ? savedPlaces.length
                    : selectedPlacesByDay[placeDay].length
                }}개 선택</span
              >
              <button
                @click="placeDay === 'storage' ? resetSavedPlace() : resetSelectedPlaces()"
                class="text-sm text-red-500 hover:underline"
              >
                초기화
              </button>
            </div>
            <div
              class="overflow-y-auto max-h-[47rem] bg-gray-100 rounded-lg p-4 shadow-md scroll smooth"
            >
              <div
                v-if="
                  (placeDay === 'storage' ? savedPlaces : selectedPlacesByDay[placeDay]).length > 0
                "
              >
                <!-- 선택된 장소 리스트 -->
                <transition-group
                  :key="placeDay"
                  name="slide-right"
                  appear
                  tag="ul"
                  class="flex-1 overflow-auto space-y-2"
                >
                  <li
                    v-for="(p, i) in placeDay === 'storage'
                      ? savedPlaces
                      : selectedPlacesByDay[placeDay]"
                    :key="p.instanceId"
                    class="bg-white h-[5rem] rounded-lg shadow flex items-center justify-between px-4 cursor-pointer hover:bg-blue-100"
                    :class="{
                      'hover:bg-pink-100': p.contentTypeId === 1 || p.contentTypeId === 2,
                    }"
                    @click="onSidebarPlaceClick(p)"
                  >
                    <img
                      :src="p.firstImage1 ? p.firstImage1 : getDefaultImage(p.contentTypeId)"
                      class="w-12 h-12 rounded mr-4"
                    />
                    <div class="flex-1">
                      <p class="truncate">{{ p.title }}</p>
                      <p
                        class="text-sm text-blue-500"
                        :class="{
                          'text-pink-500': p.contentTypeId === 1 || p.contentTypeId === 2,
                        }"
                      >
                        {{ getContentTypeName(p.contentTypeId) }}
                      </p>
                    </div>
                    <!-- 3) 우측 아이콘 -->
                    <div class="flex items-center space-x-2">
                      <!-- 삭제 버튼 -->
                      <button
                        @click.stop="
                          placeDay === 'storage'
                            ? removeSavedPlace(i)
                            : removeSelectedPlace(i, placeDay)
                        "
                        class="p-2 flex items-center justify-center rounded-md hover:bg-gray-100 transition-colors"
                      >
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          class="w-5 h-5 text-gray-400 cursor-pointer"
                          viewBox="0 0 20 20"
                          fill="currentColor"
                        >
                          +
                          <path
                            fill-rule="evenodd"
                            d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 011.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                            clip-rule="evenodd"
                          />
                        </svg>
                      </button>
                    </div>
                  </li>
                </transition-group>
              </div>
              <div
                v-if="
                  (placeDay === 'storage' ? savedPlaces : selectedPlacesByDay[placeDay]).length ===
                  0
                "
                class="flex flex-1 items-center justify-center text-gray-500 p-4"
              >
                아무것도 선택되지 않았습니다.
              </div>
            </div>
          </div>
        </div>
        <!--3-2 사이드바-->
        <div v-else-if="activeNav === 'plan'" class="p-6 flex flex-col h-full">
          <!-- 여기에 “장소 보관함” UI 추가 -->
          <div class="mt-6">
            <h3 class="text-lg font-semibold mb-2">장소 보관함</h3>
            <button @click="addToStorage()" class="mb-4 w-full bg-black text-white py-2 rounded-md">
              + 장소 추가
            </button>
            <div
              class="overflow-y-auto max-h-[47rem] bg-gray-100 rounded-lg p-4 shadow-md scroll smooth"
            >
              <draggable
                v-model="savedPlaces"
                :group="{ name: 'places', pull: true, put: true }"
                item-key="instanceId"
                :animation="200"
                :force-fallback="true"
                :fallback-on-body="true"
                @start="onDragStart"
                @end="onDragEnd"
                handle=".drag-handle"
                class="space-y-2 overflow-auto flex-1"
              >
                <template #item="{ element: p }">
                  <div class="space-y-1">
                    <div
                      class="drag-handle cursor-move bg-white h-[5rem] rounded-lg shadow flex items-center justify-between px-4"
                    >
                      <img :src="p.firstImage1" class="w-12 h-12 rounded mr-4" />
                      <div class="flex-1">
                        <p class="truncate">{{ p.title }}</p>
                        <p class="text-sm text-pink-500">
                          {{ getContentTypeName(p.contentTypeId) }}
                        </p>
                      </div>
                      <!-- 3) 우측 아이콘 -->
                      <div class="flex items-center space-x-2">
                        <!-- drag handle 아이콘 -->
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          class="w-5 h-5 text-gray-400 translate-y-[1px]"
                          fill="currentColor"
                          viewBox="0 0 20 20"
                        >
                          <path
                            d="M7 4a1 1 0 100 2 1 1 0 000-2zm0 4a1 1 0 100 2 1 1 0 000-2zm0 4a1 1 0 100 2 1 1 0 000-2zm6-8a1 1 0 100 2 1 1 0 000-2zm0 4a1 1 0 100 2 1 1 0 000-2zm0 4a1 1 0 100 2 1 1 0 000-2z"
                          />
                        </svg>
                        <!-- 삭제 버튼 -->
                        <button
                          @click="removeSavedPlace(index)"
                          class="p-2 flex items-center justify-center rounded-md hover:bg-gray-100 transition-colors"
                        >
                          <svg
                            xmlns="http://www.w3.org/2000/svg"
                            class="w-5 h-5 text-gray-400 cursor-pointer"
                            viewBox="0 0 20 20"
                            fill="currentColor"
                          >
                            +
                            <path
                              fill-rule="evenodd"
                              d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 011.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                              clip-rule="evenodd"
                            />
                          </svg>
                        </button>
                      </div>
                    </div>
                    <div
                      :class="'flex h-[30px] items-center justify-center space-x-2 bg-transparent'"
                    ></div>
                  </div>
                </template>
                <!-- 2) footer 슬롯에 빈 상태 메시지 -->
                <template #footer>
                  <div
                    class="flex items-center justify-center text-gray-400 italic h-64 w-full select-none"
                    v-if="savedPlaces.length === 0"
                  >
                    <span class="text-gray-500">보관함에 아무것도 없습니다.</span>
                  </div>
                </template>
              </draggable>
            </div>
          </div>
        </div>
        <!--3-3 사이드바-->

        <div v-else-if="activeNav === 'accommodation'" class="p-6 flex flex-col h-full">
          <div class="mt-6">
            <h3 class="text-lg font-semibold mb-4">숙소 목록</h3>

            <!-- 등록된 모든 숙소 목록 -->
            <div class="h-[16rem] overflow-y-auto bg-gray-100 rounded-lg p-4 shadow-md mb-12">
              <div v-if="accommodations.length > 0">
                <div
                  v-for="(accommodation, index) in accommodations"
                  :key="index"
                  class="bg-white rounded-lg shadow-sm p-3 mb-3"
                >
                  <div class="flex items-center justify-between">
                    <div class="flex-1">
                      <p class="font-medium text-gray-900">{{ accommodation.name }}</p>
                      <p class="text-xs text-gray-500">{{ accommodation.address }}</p>
                      <div class="flex items-center mt-1 space-x-2">
                        <div class="space-y-1">
                          <div
                            v-for="(group, groupIdx) in getAccommodationUsageGrouped(index)"
                            :key="groupIdx"
                            class="flex flex-wrap gap-1"
                          >
                            <span
                              v-for="(type, typeIdx) in group.types"
                              :key="typeIdx"
                              :class="[
                                'text-xs px-2 py-0.5 rounded-full',
                                type === '시작'
                                  ? 'bg-green-100 text-green-800'
                                  : 'bg-red-100 text-red-800',
                              ]"
                            >
                              {{ group.day }}일차 {{ type }}
                            </span>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="flex items-center space-x-2">
                      <button
                        @click="showAccommodationOnMap(accommodation)"
                        class="w-8 h-8 flex-shrink-0 flex items-center justify-center border rounded text-gray-400 hover:text-blue-600 hover:border-blue-600"
                        title="지도에서 보기"
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
                            d="M9 20l-5.447-2.724A1 1 0 013 16.382V5.618a1 1 0 011.447-.894L9 7m0 13l6-3m-6 3V7m6 10l4.553 2.276A1 1 0 0021 18.382V7.618a1 1 0 00-.553-.894L15 4m0 13V4m0 0L9 7"
                          />
                        </svg>
                      </button>
                      <button
                        @click="removeAccommodation(index)"
                        class="w-8 h-8 flex-shrink-0 flex items-center justify-center border rounded text-gray-400 hover:text-red-600 hover:border-red-600"
                        title="삭제"
                      >
                        ✕
                      </button>
                    </div>
                  </div>
                </div>
              </div>
              <div v-else class="flex items-center justify-center h-full text-gray-500">
                등록된 숙소가 없습니다
              </div>
            </div>
            <!-- 시작 장소 -->
            <div class="mb-10">
              <div class="flex items-center justify-between mb-2">
                <h3 class="text-sm font-medium text-gray-700">
                  {{ accommodationDay }}일차 시작 장소
                </h3>

                <button
                  @click="clearStartLocation"
                  class="text-xs text-red-500 hover:underline"
                  v-if="dayAccommodations[accommodationDay]?.[0].index !== -1"
                >
                  초기화
                </button>
              </div>
              <div class="bg-gray-100 rounded-lg p-4 shadow-md">
                <div v-if="dayAccommodations[accommodationDay]?.[0].index !== -1">
                  <div class="bg-white rounded-lg shadow-sm p-3">
                    <div class="flex items-center justify-between">
                      <div>
                        <p class="font-medium text-gray-900">
                          {{ accommodations[dayAccommodations[accommodationDay][0].index]?.name }}
                        </p>
                        <p class="text-xs text-gray-500">
                          {{
                            accommodations[dayAccommodations[accommodationDay][0].index]?.address
                          }}
                        </p>
                      </div>
                      <button
                        @click="clearStartLocation"
                        class="w-8 h-8 flex-shrink-0 flex items-center justify-center border rounded text-gray-400 hover:text-red-600 hover:border-red-600"
                      >
                        ✕
                      </button>
                    </div>
                  </div>
                </div>
                <div v-else class="flex items-center justify-center h-16 text-gray-500">
                  시작 장소가 설정되지 않았습니다
                </div>
              </div>
            </div>

            <!-- 종료 장소 -->
            <div>
              <div class="flex items-center justify-between mb-2">
                <h3 class="text-sm font-medium text-gray-700">
                  {{ accommodationDay }}일차 종료 장소
                </h3>
                <button
                  @click="clearEndLocation"
                  class="text-xs text-red-500 hover:underline"
                  v-if="dayAccommodations[accommodationDay]?.[1].index !== -1"
                >
                  초기화
                </button>
              </div>
              <div class="bg-gray-100 rounded-lg p-4 shadow-md">
                <div v-if="dayAccommodations[accommodationDay]?.[1].index !== -1">
                  <div class="bg-white rounded-lg shadow-sm p-3">
                    <div class="flex items-center justify-between">
                      <div>
                        <p class="font-medium text-gray-900">
                          {{ accommodations[dayAccommodations[accommodationDay][1].index]?.name }}
                        </p>
                        <p class="text-xs text-gray-500">
                          {{
                            accommodations[dayAccommodations[accommodationDay][1].index]?.address
                          }}
                        </p>
                      </div>
                      <button
                        @click="clearEndLocation"
                        class="w-8 h-8 flex-shrink-0 flex items-center justify-center border rounded text-gray-400 hover:text-red-600 hover:border-red-600"
                      >
                        ✕
                      </button>
                    </div>
                  </div>
                </div>
                <div v-else class="flex items-center justify-center h-16 text-gray-500">
                  종료 장소가 설정되지 않았습니다
                </div>
              </div>
            </div>
          </div>
        </div>
      </aside>
    </div>
    <!-- 장소 상세 모달 -->
    <PlaceDetailModal
      :visible="showPlaceDetail"
      :place="selectedPlace"
      @close="closePlaceDetail"
      @add-to-trip="addPlace"
      @add-to-wishlist="addToWishlist"
    />
  </div>
  <ChatBot class="fixed" style="bottom: 60px; right: 40px" />
</template>

<script setup>
import { ref, reactive, computed, onMounted, onUnmounted, watch, nextTick } from 'vue'
import { useRouter, useRoute, onBeforeRouteLeave } from 'vue-router'
import draggable from 'vuedraggable'
import PlaceDetailModal from './PlaceDetailModal.vue'
import { kakaoMultiRoute, kakaoSingleRoute, googleTransitRoute } from '@/utils/routeApi'
import { useUserStore } from '@/stores/user'
import { getDefaultImage } from '@/utils/util'
import ChatBot from '@/components/ChatBot.vue'

import api from '@/api'

const router = useRouter()
const route = useRoute()

// ─── 페이지네비게이션 상태 ───
const pageSize = 20
const currentPage = ref(1)
const hasMore = ref(true)
const loadingMore = ref(false)

// 탭 → 백엔드 type 파라미터 매핑
const typeParamMap = {
  '전체 보기': null,
  관광지: 'places',
  식당: 'restaurant',
  카페: 'cafe',
}

let accommodationMap = null
let accommodationMarker = null

const tripName = ref(route.query.tripName || '')
const tripDays = ref(Number(route.query.tripDays) || 1)
const tripStart = ref(route.query.tripStart || '')

const savedScheduleId = route.params.scheduleId
const schedules = ref(null)
const userStore = useUserStore()

// 저장된 데이터를 컴포넌트 상태에 매핑하는 함수
const mapSavedDataToState = (savedData) => {
  try {
    // 기본 여행 정보 매핑
    if (savedData.tripName) {
      tripName.value = savedData.tripName
    }

    if (savedData.tripDays) {
      const days = parseInt(savedData.tripDays)
      tripDays.value = days
      maxDays.value = days

      // 새로운 일수에 맞게 reactive 객체들 재구성
      Object.keys(selectedPlacesByDay).forEach((key) => {
        delete selectedPlacesByDay[key]
      })
      Object.keys(dayAccommodations).forEach((key) => {
        delete dayAccommodations[key]
      })
      Object.keys(startTimes).forEach((key) => {
        delete startTimes[key]
      })

      for (let i = 1; i <= days; i++) {
        selectedPlacesByDay[i] = []
        dayAccommodations[i] = [
          { index: -1, stayHour: 0, stayMin: 0, time: '', travelCar: '', travelBus: '' },
          { index: -1, stayHour: 0, stayMin: 0, time: '', travelCar: '', travelBus: '' },
        ]
        startTimes[i] = '09:00'
      }
    }

    if (savedData.tripStart) {
      tripStart.value = savedData.tripStart
    }

    // 숙소 정보 매핑
    if (savedData.accommodations && Array.isArray(savedData.accommodations)) {
      accommodations.value = [...savedData.accommodations]
    }

    // 일차별 장소 정보 매핑
    if (savedData.itinerary) {
      Object.keys(savedData.itinerary).forEach((dayKey) => {
        const dayNumber = parseInt(dayKey.replace('day', ''))
        if (dayNumber && savedData.itinerary[dayKey]) {
          selectedPlacesByDay[dayNumber] = [...savedData.itinerary[dayKey]]
        }
      })
    }

    // 일차별 숙소 설정 매핑 (수정 완료된 버전)
    if (savedData.dayAccommodations) {
      Object.entries(savedData.dayAccommodations).forEach(([dayKey, accomArr]) => {
        // "day1" → 1
        const dayNum = parseInt(dayKey.replace(/^day/, ''), 10)
        // 숙소 배열이 실제로 [ {…},{…} ] 형태인지 확인
        if (dayNum && Array.isArray(accomArr) && accomArr.length >= 2) {
          // 배열 그대로 재생성
          dayAccommodations[dayNum] = accomArr
        }
      })

      console.log('최종 dayAccommodations:', dayAccommodations)
    }

    // 시작 시간 매핑
    if (savedData.startTimes) {
      Object.keys(savedData.startTimes).forEach((dayKey) => {
        const dayNumber = parseInt(dayKey.replace(/^day/, ''), 10)
        if (dayNumber && savedData.startTimes[dayKey]) {
          startTimes[dayNumber] = savedData.startTimes[dayKey]
        }
      })
    }

    console.log('데이터 매핑 완료:', {
      tripName: tripName.value,
      tripDays: tripDays.value,
      selectedPlacesByDay,
      accommodations: accommodations.value,
      dayAccommodations,
    })
  } catch (error) {
    console.error('저장된 데이터 매핑 중 오류:', error)
  }
}

// 드롭다운 상태
const showUserDropdown = ref(false)

// 로그아웃 함수
const logout = () => {
  userStore.logout() // 사용자 스토어에서 로그아웃 처리
  showUserDropdown.value = false // 드롭다운 닫기
  router.push('/login') // 로그인 페이지로 이동
}

// 마이페이지로 이동 함수 수정
function goToMyPage() {
  showUserDropdown.value = false // 드롭다운 닫기
  router.push({ name: 'mypage' })
}

// 버튼에 표시할 텍스트를 계산된 속성으로 정의
const buttonLabel = computed(() => {
  return activeNav.value === 'plan' ? '저장' : '➔'
})

const isSaveClicked = ref(false)

async function goNext() {
  if (activeNav.value === 'places') {
    activeNav.value = 'accommodation'
  } else if (activeNav.value === 'accommodation') {
    activeNav.value = 'plan'
  } else if (activeNav.value === 'plan') {
    // TODO: plan 단계에서 백엔드로 데이터 전송 로직 호출
    // 예) submitData()
    console.log('데이터 저장/전송 처리')
    saveTrip()
    // goNext() 안에서
    isSaveClicked.value = true
    router.push({
      name: 'mypage',
    })
  }
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
// JWT 디코딩 (토큰에서 사용자 ID 추출)
const token = localStorage.getItem('jwt')
const payload = token.split('.')[1]
const decoded = JSON.parse(atob(payload))
const userId = decoded.userId

// 4가지 값 묶어서 백엔드로 보내는 함수
const saveTrip = async () => {
  // ① reactive / ref 상태들에서 값을 꺼내서 payload로 구성
  const payload = {
    scheduleId: savedScheduleId,
    userId: userId,
    title: tripName.value,
    dayCount: tripDays.value,
    startDate: tripStart.value,
    // selectedPlacesByDay는 객체 형태이므로 그대로 넘겨도 OK
    itinerary: selectedPlacesByDay,
    dayAccommodations: dayAccommodations,
    startTimes: startTimes,
    accommodations: accommodations.value,
  }
  try {
    const res = await api.post('/api/plan/save', payload)
    console.log('저장 성공:', res.data)
    alert('일정이 성공적으로 저장되었습니다!')
  } catch (err) {
    console.error('저장 실패:', err)
    alert('저장 중 오류가 발생했습니다.')
  }
}
// 내비 상태
const activeNav = ref('places')

// 필터
const sidos = ref([
  { sido_code: 0, sido_name: '전국' },
  { sido_code: 1, sido_name: '서울' },
  { sido_code: 2, sido_name: '인천' },
  { sido_code: 3, sido_name: '대전' },
  { sido_code: 4, sido_name: '대구' },
  { sido_code: 5, sido_name: '광주' },
  { sido_code: 6, sido_name: '부산' },
  { sido_code: 7, sido_name: '울산' },
  { sido_code: 8, sido_name: '세종' },
  { sido_code: 31, sido_name: '경기도' },
  { sido_code: 32, sido_name: '강원도' },
  { sido_code: 33, sido_name: '충북' },
  { sido_code: 34, sido_name: '충남' },
  { sido_code: 35, sido_name: '경북' },
  { sido_code: 36, sido_name: '경남' },
  { sido_code: 37, sido_name: '전북' },
  { sido_code: 38, sido_name: '전남' },
  { sido_code: 39, sido_name: '제주' },
])

const selectedRegion = ref(0)
const searchQuery = ref('')

const tabs = ['전체 보기', '관광지', '식당', '카페']
const activeTab = ref(tabs[0])

// 사용자가 정할 “총 일수”
const maxDays = ref(tripDays) // 예: 3일차까지

// 현재 선택된 값: 숫자(1~N) 또는 'storage'
const placeDay = ref(1) // 장소선택 탭
const planDay = ref(1) // 일정생성 탭

// select 의 옵션 리스트: [{ label, value }, ...]
const dayOptions = computed(() => {
  const opts = []
  for (let i = 1; i <= maxDays.value; i++) {
    opts.push({ label: `${i}일차`, value: i })
  }
  // 맨 끝에 보관함 추가
  opts.push({ label: '보관함', value: 'storage' })
  return opts
})

const planDayOptions = computed(() => {
  return Array.from({ length: maxDays.value }, (_, i) => i + 1)
})

const selectedPlacesByDay = reactive({})
for (let i = 1; i <= maxDays.value; i++) {
  selectedPlacesByDay[i] = []
}

const startTimes = reactive({})
for (let i = 1; i <= maxDays.value; i++) {
  startTimes[i] = '09:00' // 기본값 설정
}

function getAccommodationUsageGrouped(index) {
  const usageMap = {}

  for (const day in dayAccommodations) {
    const dayNum = Number(day)
    const usage = []

    if (dayAccommodations[day][0].index === index) {
      usage.push('시작')
    }
    if (dayAccommodations[day][1].index === index) {
      usage.push('종료')
    }

    if (usage.length > 0) {
      usageMap[dayNum] = usage
    }
  }

  // 정렬을 위해 day 기준 정렬
  return Object.entries(usageMap)
    .sort((a, b) => a[0] - b[0])
    .map(([day, types]) => ({ day, types }))
}

// 보관된 장소를 저장할 배열
const savedPlaces = ref([])

// 숙소 관련 상태
const accommodationMapContainer = ref(null)
const accommodationSearchQuery = ref('')
const accommodationSearchResults = ref([])
const accommodations = ref([])
const accommodationDay = ref(1)

// 일차별 숙소 설정 (시작/종료 장소)
const dayAccommodations = reactive({})
for (let i = 1; i <= maxDays.value; i++) {
  dayAccommodations[i] = [
    { index: -1, stayHour: 0, stayMin: 0, time: '', travelCar: '', travelBus: '' },
    { index: -1, stayHour: 0, stayMin: 0, time: '', travelCar: '', travelBus: '' },
  ]
}

// 주소 입력을 위한 상태
const selectedAddressIndex = ref(null)
const customPlaceName = ref('')

// 숙소 검색
const searchAccommodation = () => {
  ensureAccommodationMap()
  if (!accommodationSearchQuery.value.trim()) return

  const kmaps = window.kakao.maps
  const ps = new kmaps.services.Places()

  // 검색어로 장소 검색
  ps.keywordSearch(accommodationSearchQuery.value, (data, status) => {
    if (status === kmaps.services.Status.OK) {
      accommodationSearchResults.value = data

      // 검색 결과를 지도에 표시
      accommodationMap.setCenter(new kmaps.LatLng(data[0].y, data[0].x))
      accommodationMap.setLevel(5)

      // 마커 표시
      data.forEach((place) => {
        const marker = new kmaps.Marker({
          map: accommodationMap,
          position: new kmaps.LatLng(place.y, place.x),
        })

        // 마커 클릭 이벤트
        kmaps.event.addListener(marker, 'click', () => {
          selectAccommodation(place)
        })
      })
    } else {
      // 장소 검색 실패 시 주소 검색 시도
      const geocoder = new kmaps.services.Geocoder()

      geocoder.addressSearch(accommodationSearchQuery.value, (result, geocodeStatus) => {
        if (geocodeStatus === kmaps.services.Status.OK) {
          // 주소 검색 결과를 변환하여 표시
          accommodationSearchResults.value = result.map((item) => ({
            address_name: item.address_name,
            y: item.y,
            x: item.x,
            // place_name은 없음 (주소 검색 결과이므로)
          }))

          // 검색 결과를 지도에 표시
          const accommodationMap = new kmaps.Map(accommodationMapContainer.value, {
            center: new kmaps.LatLng(result[0].y, result[0].x),
            level: 5,
          })

          // 마커 표시
          result.forEach((place) => {
            const marker = new kmaps.Marker({
              map: accommodationMap,
              position: new kmaps.LatLng(place.y, place.x),
            })
          })
        } else {
          accommodationSearchResults.value = []
        }
      })
    }
  })
}

// 숙소 선택 (지도에서 선택)
const selectAccommodation = (place) => {
  const kmaps = window.kakao.maps
  const accommodationMap = new kmaps.Map(accommodationMapContainer.value, {
    center: new kmaps.LatLng(place.y, place.x),
    level: 3,
  })

  // 선택한 장소에 마커 표시
  new kmaps.Marker({
    map: accommodationMap,
    position: new kmaps.LatLng(place.y, place.x),
  })
}

async function computeBothTransports() {
  await computeTravelTimesSelective('car')
  await computeTravelTimesSelective('public')
}

// “왼쪽에 새 아이템이 추가”될 때
async function onPlanAdd(evt) {
  // vuedraggable이 제공하는 added.element 사용
  const added = evt.added?.element
  if (!added) return

  if (added.stayHour == null) added.stayHour = 1
  if (added.stayMin == null) added.stayMin = 0
  if (added.time == null) added.time = ''
  if (added.travelCar == null) added.travelCar = ''
  if (added.travelBus == null) added.travelBus = ''

  recalcSchedule()

  // 새 아이템 앞뒤 두 구간을 초기화
  const i = evt.newIndex
  clearTravelAt(i - 1)
  clearTravelAt(i)

  await computeBothTransports()
}

// “오른쪽(savedPlaces)에 새 아이템이 추가”될 때, 필요하다면…
// function onSavedAdd(evt) {
//   const added = savedPlaces.value[evt.newIndex]
// }

// “+ 장소 추가” 버튼 클릭 핸들러
function addToStorage() {
  // 탭 전환 & 드롭다운 변경
  activeNav.value = 'places'
  placeDay.value = 'storage'

  // (필요하면) showDetailSidebar도 열기
  showDetailSidebar.value = true
}

// 휴지통 버튼 클릭 시 해당 인덱스 삭제
function removeSavedPlace(idx) {
  savedPlaces.value.splice(idx, 1)
}

function resetSavedPlace() {
  // 보관함 초기화
  savedPlaces.value = []
}

// 장소 상세 모달 관련
const showPlaceDetail = ref(false)
const selectedPlace = ref(null)
let map = null // 지도 인스턴스 저장

// 장소 클릭 핸들러
function showPlaceDetailModal(place) {
  selectedPlace.value = place
  showPlaceDetail.value = true

  // 지도 줌인
  if (map && place.lat && place.lng) {
    const kmaps = window.kakao.maps
    const moveLatLon = new kmaps.LatLng(place.lat, place.lng)
    map.setCenter(moveLatLon)
    map.setLevel(3) // 줌 레벨 설정

    // 마커 추가
    new kmaps.Marker({
      map: map,
      position: moveLatLon,
    })
  }
}

function closePlaceDetail() {
  showPlaceDetail.value = false
  selectedPlace.value = null
}

// 더미 데이터
const places = ref([])

async function fetchPage(page = 1) {
  loadingMore.value = true
  try {
    const typeParam = typeParamMap[activeTab.value]
    const res = await api.get('/api/attractions', {
      params: {
        page,
        size: pageSize,
        selectedRegion: selectedRegion.value,
        searchQuery: searchQuery.value,
        ...(typeParam ? { type: typeParam } : {}),
      },
    })
    // assuming 백엔드가 { content: [...], totalPages: N } 구조로 반환
    const { content, totalPages } = res.data
    console.log(`장소 ${page}페이지 로드 성공:`, content)
    if (page === 1) places.value = content
    else places.value.push(...content)
    hasMore.value = page < totalPages

    // 새로 추가된 것만 마커 추가
    addMarkers(content)
  } catch (err) {
    console.error('장소 로드 실패:', err)
  } finally {
    loadingMore.value = false
  }
}

function loadMore() {
  if (!hasMore.value || loadingMore.value) return
  currentPage.value++
  fetchPage(currentPage.value)
}

// 검색어나 지역 또는 탭이 바뀌면 다시 1페이지부터
watch([searchQuery, selectedRegion, activeTab], () => {
  currentPage.value = 1
  clearMarkers()
  fetchPage(1)
})

watch(
  dayAccommodations,
  () => {
    drawSelectedMarkers()
  },
  { deep: true },
)

onMounted(() => {
  fetchPage(1)
})

// --- 새로 추가된 상태 & 함수 ---
const showDetailSidebar = ref(true) // 패널 열림 여부

// 1) 인스턴스용 카운터
let nextInstanceId = 3

async function addPlace(p) {
  const instance = {
    ...p,
    instanceId: nextInstanceId++, // 자동 증분
    stayHour: 1,
    stayMin: 0,
    time: '',
    travelCar: '',
    travelBus: '',
  }
  if (placeDay.value === 'storage') {
    savedPlaces.value.push(instance)
  } else {
    // 종료장소가 있으면 그 직전, 아니면 맨 끝
    const endItem = selectedPlacesByDay[placeDay.value].find((it) => it.isEnd)
    const insertAt = endItem
      ? selectedPlacesByDay[placeDay.value].indexOf(endItem)
      : selectedPlacesByDay[placeDay.value].length
    selectedPlacesByDay[placeDay.value].splice(insertAt, 0, instance)

    // 선택된 장소에 추가
    await computeBothTransports()
  }
  recalcSchedule()
}

// 카카오 맵 초기화
const mapContainer = ref(null)
// 드롭다운 외부 클릭 감지
const handleClickOutside = (event) => {
  if (showUserDropdown.value && !event.target.closest('.relative')) {
    showUserDropdown.value = false
  }
}

onMounted(() => {
  const savedSchedules = localStorage.getItem('editTripData')
  if (savedSchedules) {
    schedules.value = JSON.parse(savedSchedules)
    console.log('로컬스토리지에서 일정 데이터 조회:', schedules.value, savedScheduleId)

    // 기존 여행 데이터를 컴포넌트 상태에 매핑
    mapSavedDataToState(schedules.value)

    localStorage.removeItem('editTripData') // 로컬스토리지에서 삭제
  } else {
    // fallback (백엔드에서 다시 조회)
    console.warn('저장된 일정 없음')
  }

  // 기존 로직들
  recalcSchedule()
  fetchPage(1)

  // 외부 클릭 이벤트 리스너 추가
  document.addEventListener('click', handleClickOutside)
})

// 컴포넌트 언마운트 시 이벤트 리스너 제거
onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
})

function ensureAccommodationMap() {
  if (accommodationMap) return
  const kmaps = window.kakao.maps
  accommodationMap = new kmaps.Map(accommodationMapContainer.value, {
    center: new kmaps.LatLng(33.3617, 126.5292),
    level: 9,
  })
}

// 숙소 지도 초기화
onMounted(() => {
  window.kakao?.maps.load(() => {
    if (accommodationMapContainer.value) {
      const kmaps = window.kakao.maps
      accommodationMap = new kmaps.Map(accommodationMapContainer.value, {
        center: new kmaps.LatLng(33.3617, 126.5292),
        level: 9,
      })
    }
    addMarkers(places.value)
    drawSelectedMarkers()
  })
})

// 1) 이전에 찍어둔 마커/오버레이를 모두 지우는 함수
function clearSelectedMarkers() {
  selectedMarkers.forEach((obj) => {
    // Marker, Circle 둘 다 지원
    if (obj.setMap) obj.setMap(null)
    else if (obj.setMap) obj.setMap(null)
  })
  selectedMarkers = []
}

// activeNav 변경 감지하여 지도 초기화
watch(activeNav, (newNav) => {
  if (newNav === 'accommodation') {
    // 다음 틱에 지도 초기화 (DOM이 업데이트된 후)
    nextTick(() => {
      if (accommodationMapContainer.value) {
        window.kakao?.maps.load(() => {
          const kmaps = window.kakao.maps
          const accommodationMap = new kmaps.Map(accommodationMapContainer.value, {
            center: new kmaps.LatLng(33.3617, 126.5292), // 제주도 중심
            level: 9,
          })
        })
      }
    })
  }
  clearMarkers()
  addMarkers(places.value)
  drawSelectedMarkers()
})

const goHome = () => {
  router.push('/')
}

async function removeSelectedPlace(idx, day) {
  const list = selectedPlacesByDay[day]
  // 1) 삭제 전, 바로 앞 구간을 초기화
  clearTravelAt(idx - 1)

  // 2) 실제 삭제
  list.splice(idx, 1)

  // 3) 스케줄·시간 재계산
  recalcSchedule()
  await computeTravelTimesSelective(transportModeValue.value) // async 함수니까 필요하다면 await
}
function resetSelectedPlaces() {
  selectedPlacesByDay[placeDay.value] = []
}

const toggleBtnLeft = computed(() => {
  return showDetailSidebar.value
    ? '396px' // 3번째 사이드바 열렸을 때
    : '-18px' // 평소에는 2번째 사이드바 끝
})

// 시작 시간 관리 (raw: 24시간, formatted: 12시간 및 오전/오후)
const startTimeRaw = computed({
  get: () => startTimes[planDay.value],
  set: (val) => {
    startTimes[planDay.value] = val
  },
})
const transportModeValue = ref('car') // 'car' 또는 'public'

async function optimizeRouteHandler() {
  const day = planDay.value
  const middlePlaces = [...selectedPlacesByDay[day]]
  const startIdx = dayAccommodations[day][0].index
  const endIdx = dayAccommodations[day][1].index

  // 시작 또는 종료 장소가 없으면 경고
  if (startIdx === -1 || endIdx === -1) {
    alert('시작 또는 종료 장소를 먼저 설정해주세요.')
    return
  }
  // 중간에 최적화할 장소가 없으면 경고
  if (middlePlaces.length <= 1) {
    alert('중간에 최적화할 장소가 없습니다.')
    return
  }

  // 모든 포인트 배열: [start, ...middles, end]
  const points = [accommodations.value[startIdx], ...middlePlaces, accommodations.value[endIdx]]
  const n = points.length
  const isCar = transportModeValue.value === 'car'

  const dist = Array.from({ length: n }, () => Array(n).fill(0))
  const jobs = []
  for (let i = 0; i < n; i++) {
    for (let j = i + 1; j < n; j++) {
      jobs.push(
        (async () => {
          const d = isCar
            ? await kakaoSingleRoute(points[i], points[j])
            : await googleTransitRoute(points[i], points[j])
          dist[i][j] = dist[j][i] =
            d > 0 ? d : estimateDuration(points[i], points[j], isCar ? 'car' : 'public')
        })(),
      )
    }
  }
  await Promise.all(jobs)

  // 2) DP + 비트마스크로 TSP 해결
  const M = 1 << n // 2^n
  const dp = Array.from({ length: M }, () => Array(n).fill(Infinity))
  const prev = Array.from({ length: M }, () => Array(n).fill(-1))

  // 출발점(0번)만 방문한 상태
  dp[1][0] = 0

  for (let mask = 1; mask < M; mask++) {
    for (let u = 0; u < n; u++) {
      if (!(mask & (1 << u))) continue
      for (let v = 1; v < n; v++) {
        // 마지막엔 반드시 end(n-1)여야 하므로, mask가 모든 비트 켜진 상태가 아니면 end로 가지 않음
        if (mask & (1 << v)) continue
        if (v === n - 1 && (mask !== M - 1) ^ 1) continue
        const nextMask = mask | (1 << v)
        const cost = dp[mask][u] + dist[u][v]
        if (cost < dp[nextMask][v]) {
          dp[nextMask][v] = cost
          prev[nextMask][v] = u
        }
      }
    }
  }

  // 3) 종료점(n-1)에서 되돌아가며 경로 복원
  let mask = (1 << n) - 1
  let cur = n - 1
  const order = []
  while (cur !== -1) {
    order.push(cur)
    const p = prev[mask][cur]
    mask ^= 1 << cur
    cur = p
  }
  order.reverse() // [0, ..., n-1]

  // 중간 인덱스만 골라서 selectedPlacesByDay 갱신
  const optimized = order.slice(1, -1).map((i) => points[i])
  selectedPlacesByDay[day] = optimized

  // 일정 및 이동시간 재계산
  for (let i = -1; i < selectedPlacesByDay[planDay.value].length; i++) {
    clearTravelAt(i)
  }
  await computeTravelTimesSelective(transportModeValue.value)
  recalcSchedule()
  alert('경로가 최적화되었습니다.')
}

watch(planDay, async () => {
  recalcSchedule()
  await computeTravelTimesSelective(transportModeValue.value)
})

watch(startTimeRaw, async () => {
  // ➡️ time input(type="time") 값이 바뀔 때마다
  recalcSchedule()
  await computeTravelTimesSelective(transportModeValue.value)
})

watch(transportModeValue, async () => {
  recalcSchedule()
  await computeTravelTimesSelective(transportModeValue.value)
})

onMounted(() => {
  recalcSchedule()
})

// — 시간 파싱·포맷 헬퍼 —
function parseHM(str) {
  const [h, m] = str.split(':').map(Number)
  return h * 60 + m
}
function formatHM(mins) {
  const h = Math.floor(mins / 60)
  const m = mins % 60
  return `${String(h).padStart(2, '0')}:${String(m).padStart(2, '0')}`
}

// 일정 전체를 순서대로 재계산
function recalcSchedule() {
  // startTimeRaw를 기준으로
  let cursor = parseHM(startTimeRaw.value)
  const list = selectedPlacesByDay[planDay.value]
  if (dayAccommodations[planDay.value][0].index !== -1) {
    // 시작 장소가 설정되어 있다면, 그 시간부터 시작
    const stayMin =
      dayAccommodations[planDay.value][0].stayHour * 60 +
      dayAccommodations[planDay.value][0].stayMin
    const travelMin =
      Number(
        transportModeValue.value === 'car'
          ? dayAccommodations[planDay.value][0].travelCar
          : dayAccommodations[planDay.value][0].travelBus,
      ) || 0
    const start = cursor
    const end = start + stayMin

    dayAccommodations[planDay.value][0].time = `${formatHM(start)}-${formatHM(end)}`
    cursor = end + travelMin
  }
  list.forEach((item) => {
    const stayMin = item.stayHour * 60 + item.stayMin
    const travelMin =
      Number(transportModeValue.value === 'car' ? item.travelCar : item.travelBus) || 0

    const start = cursor
    const end = start + stayMin

    item.time = `${formatHM(start)}-${formatHM(end)}`
    cursor = end + travelMin
  })
  if (dayAccommodations[planDay.value][1].index !== -1) {
    // 종료 장소가 설정되어 있다면, 종료장소까지 계산
    const stayMin =
      dayAccommodations[planDay.value][1].stayHour * 60 +
      dayAccommodations[planDay.value][1].stayMin
    const start = cursor
    const end = start + stayMin

    dayAccommodations[planDay.value][1].time = `${formatHM(start)}-${formatHM(end)}`
  }
}

// 드래그 중 플래그
const isDragging = ref(false)

function onDragStart() {
  isDragging.value = true
}

async function onDragEnd(evt) {
  isDragging.value = false
  recalcSchedule()

  const oldI = evt.oldIndex
  const newI = evt.newIndex
  // 두 위치 각각 앞뒤 구간을 초기화
  ;[oldI, newI].forEach((idx) => {
    clearTravelAt(idx - 1)
    clearTravelAt(idx)
  })

  await computeTravelTimesSelective(transportModeValue.value)
}

// 주소 선택 시 커스텀 장소명 입력 활성화
//const selectedAddressIndex = ref(null)
//const customPlaceName = ref('')

const selectAddressForInput = (index) => {
  if (selectedAddressIndex.value === index) {
    // 이미 선택된 주소라면 장소명 입력 후 등록 처리
    if (customPlaceName.value.trim()) {
      addCustomAccommodation(accommodationSearchResults.value[index])
    }
  } else {
    // 새로 선택한 주소라면 입력 폼 표시
    selectedAddressIndex.value = index
    customPlaceName.value = ''
  }
}

// 숙소 추가
const addAccommodation = (place) => {
  accommodations.value.push({
    id: Date.now(),
    name: place.place_name || place.address_name,
    address: place.address_name,
    phone: place.phone || '',
    latitude: place.y,
    longitude: place.x,
  })
}

// 숙소 삭제
const removeAccommodation = (index) => {
  // 삭제 전에 해당 숙소를 참조하는 일차별 설정 초기화
  for (const day in dayAccommodations) {
    if (dayAccommodations[day][0].index === index) {
      dayAccommodations[day][0].index = -1
    }
    if (dayAccommodations[day][1].index === index) {
      dayAccommodations[day][1].index = -1
    }

    // 삭제 후 인덱스 조정 (삭제된 인덱스보다 큰 인덱스는 1 감소)
    if (dayAccommodations[day][0].index > index) {
      dayAccommodations[day][0].index = Number(dayAccommodations[day][0].index) - 1
    }
    if (dayAccommodations[day][1].index > index) {
      dayAccommodations[day][1].index = Number(dayAccommodations[day][1].index) - 1
    }
  }

  accommodations.value.splice(index, 1)
  recalcSchedule()
}

// 커스텀 숙소 추가
const addCustomAccommodation = (result) => {
  if (!customPlaceName.value.trim()) {
    alert('장소명을 입력해주세요.')
    return
  }

  accommodations.value.push({
    id: Date.now(),
    name: customPlaceName.value,
    address: result.address_name,
    lat: result.y,
    lng: result.x,
  })

  // 입력 상태 초기화
  selectedAddressIndex.value = null
  customPlaceName.value = ''
}

// 시작 장소 설정
const setAsStartLocation = (index) => {
  // 이미 다른 숙소가 현재 일차의 시작 장소로 설정되어 있는지 확인
  if (
    dayAccommodations[accommodationDay.value][0].index !== -1 &&
    dayAccommodations[accommodationDay.value][0].index !== index
  ) {
    if (confirm('이미 다른 숙소가 시작 장소로 설정되어 있습니다. 변경하시겠습니까?')) {
      dayAccommodations[accommodationDay.value][0].index = index
    }
  } else {
    dayAccommodations[accommodationDay.value][0].index = index
  }
  computeTravelTimesSelective(transportModeValue.value)
  recalcSchedule()
}

// 종료 장소 설정
const setAsEndLocation = (index) => {
  // 이미 다른 숙소가 현재 일차의 종료 장소로 설정되어 있는지 확인
  if (
    dayAccommodations[accommodationDay.value][1].index !== -1 &&
    dayAccommodations[accommodationDay.value][1].index !== index
  ) {
    if (confirm('이미 다른 숙소가 종료 장소로 설정되어 있습니다. 변경하시겠습니까?')) {
      dayAccommodations[accommodationDay.value][1].index = index
    }
  } else {
    dayAccommodations[accommodationDay.value][1].index = index
  }
  computeTravelTimesSelective(transportModeValue.value)
  recalcSchedule()
}

// 시작 장소 초기화
const clearStartLocation = () => {
  dayAccommodations[accommodationDay.value][0].index = -1
  recalcSchedule()
}

// 종료 장소 초기화
const clearEndLocation = () => {
  dayAccommodations[accommodationDay.value][1].index = -1
  recalcSchedule()
}

// 지도에서 숙소 위치 보기
const showAccommodationOnMap = (accom) => {
  ensureAccommodationMap()
  if (!accommodationMap) return
  console.log(accom)
  const kmaps = window.kakao.maps
  const pos = new kmaps.LatLng(accom.latitude, accom.longitude)
  accommodationMap.setCenter(pos)
  accommodationMap.setLevel(3)

  // 이전 마커 지우고
  if (accommodationMarker) accommodationMarker.setMap(null)
  // 새 마커만 띄우기
  accommodationMarker = new kmaps.Marker({
    map: accommodationMap,
    position: pos,
  })
}

function addToWishlist(place) {
  alert(`${place.name}을(를) 위시리스트에 추가했습니다!`)
}

// 특정 구간(idx → idx+1)의 이동 시간을 초기화하는 헬퍼
function clearTravelAt(idx) {
  const list = selectedPlacesByDay[planDay.value]
  if (idx >= 0 && idx < list.length) {
    if (transportModeValue.value === 'car') {
      list[idx].travelCar = ''
    } else {
      list[idx].travelBus = ''
    }
  } else if (idx === -1) {
    // 첫 번째 장소 이전 → 시작 장소와 연결
    if (dayAccommodations[planDay.value]?.[0]) {
      if (transportModeValue.value === 'car') {
        dayAccommodations[planDay.value][0].travelCar = ''
      } else {
        dayAccommodations[planDay.value][0].travelBus = ''
      }
    }
  }
}

// 2) travel 계산 헬퍼 함수 추가
//    - gaps: travel값이 비어있는 구간({from,to,idx})만 모아서 처리
async function computeTravelTimesSelective(mode) {
  const list = selectedPlacesByDay[planDay.value]
  const isCar = mode ? mode === 'car' : transportModeValue.value === 'car'
  const field = isCar ? 'travelCar' : 'travelBus'

  // 1) 계산이 필요한 구간만 수집
  const gaps = []
  let durations = []
  for (let i = 1; i < list.length; i++) {
    if (!list[i - 1][field]) gaps.push({ from: list[i - 1], to: list[i], idx: i - 1 })
  }
  if (gaps.length !== 0) {
    // 2) 우선 다중 경유지 호출 시도
    if (isCar && gaps.length > 1) {
      try {
        durations = await kakaoMultiRoute(gaps.map((g) => ({ from: g.from, to: g.to })))
        // 이상 없이 전체 구간 계산됐는지 확인
        if (durations.length !== gaps.length) {
          throw new Error('multi-route incomplete')
        }
      } catch (e) {
        console.warn('다중 길찾기 실패, 단일 구간으로 폴백:', e)
      }
    }

    // 3) durations 배열이 비었으면 개별 호출 수행
    if (durations.length === 0) {
      for (const g of gaps) {
        let d = 0
        try {
          if (isCar) {
            // car 모드: kakaoSingleRoute
            d = await kakaoSingleRoute(g.from, g.to)
          } else {
            // public 모드: googleTransitRoute (always 개별 호출)
            d = await googleTransitRoute(g.from, g.to)
          }
        } catch {
          d = 0
        }
        // API 실패 시 대략 계산
        if (d <= 0) {
          const isSamePlace =
            g.from?.latitude === g.to?.latitude && g.from?.longitude === g.to?.longitude
          if (isSamePlace) {
            d = 1 // 같은 장소라도 최소 이동 시간 1분 부여
            console.warn('같은 장소 → 최소 이동 시간 1분 설정')
          } else {
            d = estimateDuration(g.from, g.to, isCar ? 'car' : 'public')
            console.warn(`API 실패, 대략 계산된 시간: ${d}분`)
          }
        }
        durations.push(d)
      }
    }
  }

  const startAcc = dayAccommodations[planDay.value]?.[0]
  let startD = -1
  if (
    startAcc?.index !== -1 &&
    selectedPlacesByDay[planDay.value].length > 0 &&
    startAcc[field] === ''
  ) {
    const from = accommodations.value[startAcc.index]
    const to = selectedPlacesByDay[planDay.value][0]
    let d = 0
    try {
      if (isCar) {
        // car 모드: kakaoSingleRoute
        d = await kakaoSingleRoute(from, to)
      } else {
        // public 모드: googleTransitRoute (always 개별 호출)
        d = await googleTransitRoute(from, to)
      }
    } catch {
      d = 0
    }

    if (d <= 0) {
      const isSamePlace = from?.latitude === to?.latitude && from?.longitude === to?.longitude
      if (isSamePlace) {
        d = 1 // 같은 장소라도 최소 이동 시간 1분 부여
        console.warn('같은 장소 → 최소 이동 시간 1분 설정')
      } else {
        d = estimateDuration(from, to, isCar ? 'car' : 'public')
        console.warn(`API 실패, 대략 계산된 시간: ${d}분`)
      }
    }
    startD = d
  }

  // ─── 2) 마지막 장소와 종료 장소 이동 시간 계산 ───
  const endAcc = dayAccommodations[planDay.value]?.[1]
  const dayList = selectedPlacesByDay[planDay.value]
  const lastPlace = dayList.at(-1)
  let endD = -1
  if (
    endAcc?.index !== -1 &&
    dayList.length > 0 &&
    (lastPlace.travelCar === '' || lastPlace.travelBus === '')
  ) {
    const from = lastPlace
    const to = accommodations.value[endAcc.index]
    let d = 0
    try {
      if (isCar) {
        // car 모드: kakaoSingleRoute
        d = await kakaoSingleRoute(from, to)
      } else {
        // public 모드: googleTransitRoute (always 개별 호출)
        d = await googleTransitRoute(from, to)
      }
    } catch {
      d = 0
    }

    if (d <= 0) {
      const isSamePlace = from?.latitude === to?.latitude && from?.longitude === to?.longitude
      if (isSamePlace) {
        d = 1 // 같은 장소라도 최소 이동 시간 1분 부여
        console.warn('같은 장소 → 최소 이동 시간 1분 설정')
      } else {
        d = estimateDuration(from, to, isCar ? 'car' : 'public')
        console.warn(`API 실패, 대략 계산된 시간: ${d}분`)
      }
    }
    endD = d
  }

  // 4) 실패 구간(0 또는 undefined)은 건너뛰고, 성공한 구간만 적용
  gaps.forEach((g, i) => {
    const d = durations[i]
    if (d >= 0) {
      list[g.idx][field] = d
    } else {
      console.warn(`구간 ${i}번 계산 불가 (에러 코드 101/102 등), 빈값 유지`)
    }
  })
  if (startAcc) {
    startAcc[field] = startD != -1 ? startD : startAcc[field]
  }

  if (lastPlace) {
    lastPlace[field] = endD != -1 ? endD : lastPlace[field]
  }

  // 5) 시간 전체 재계산
  recalcSchedule()
}

/**
 * 두 좌표 사이 직선 거리(km) 계산 (Haversine)
 */
function haversineDistance(from, to) {
  const toRad = (deg) => (deg * Math.PI) / 180
  const R = 6371 // 지구 반경(km)
  const dLat = toRad(to.latitude - from.latitude)
  const dLng = toRad(to.longitude - from.longitude)
  const a =
    Math.sin(dLat / 2) ** 2 +
    Math.cos(toRad(from.latitude)) * Math.cos(toRad(to.latitude)) * Math.sin(dLng / 2) ** 2
  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
  return R * c
}

/**
 * 대략적인 이동 시간(분) 계산
 * @param {{lat,lng}} from
 * @param {{lat,lng}} to
 * @param {'car'|'public'} mode
 */
function estimateDuration(from, to, mode = 'car') {
  const dist = haversineDistance(from, to) // km
  const speed = mode === 'car' ? 50 : 25 // km/h
  return Math.ceil((dist / speed) * 60) // 분 단위 반환
}

// 기존 markers 배열 아래에 추가
const baseMarkers = [] // places 기본 마커
let selectedMarkers = [] // 일차별 선택된 장소 마커

function clearMarkers() {
  baseMarkers.forEach((m) => m.setMap(null))
  baseMarkers.length = 0
}

function drawSelectedMarkers() {
  if (!map || typeof map.getLevel !== 'function') return
  clearSelectedMarkers()

  const kmaps = window.kakao.maps
  const day = activeNav.value === 'places' ? placeDay.value : planDay.value
  const list = selectedPlacesByDay[day] || []

  // 지도 레벨에 따라 크기를 조정해도 되고, 고정 사이즈(예: 40px)로 해도 좋습니다.
  const level = map.getLevel()
  const baseSize = 50 // 최대 크기
  const minSize = 30 // 최소 크기
  const size = Math.max(minSize, baseSize - level * 2)

  list.forEach((p, idx) => {
    const pos = new kmaps.LatLng(p.latitude, p.longitude)
    const html = `
      <div
        style="
          width: ${size}px;
          height: ${size}px;
          line-height: ${size}px;
          background: #FF4081;
          border-radius: 50%;
          color: white;
          font-weight: bold;
          text-align: center;
          font-size: ${Math.floor(size / 2)}px;
          transform: translate(-50%, -50%);
        "
      >
        ${idx + 1}
      </div>
    `
    const overlay = new kmaps.CustomOverlay({
      map,
      position: pos,
      content: html,
    })
    selectedMarkers.push(overlay)
  })

  // 2) 숙소들: 파란 원에 S/E
  const accom = accommodations.value
  const accStartIdx = dayAccommodations[day]?.[0]?.index
  const accEndIdx = dayAccommodations[day]?.[1]?.index

  ;[
    [accStartIdx, 'S'],
    [accEndIdx, 'E'],
  ].forEach(([i, label]) => {
    if (i != null && i >= 0 && accom[i]) {
      const loc = accom[i]
      const pos = new kmaps.LatLng(loc.latitude, loc.longitude)
      const html = `
        <div style="
          width: 40px;
          height: 40px;
          line-height: 40px;
          background: #9370DB;
          border-radius: 50%;
          color: white;
          font-weight: bold;
          text-align: center;
          font-size: 18px;
          transform: translate(-50%, -50%);
        ">
          ${label}
        </div>`
      const overlay = new kmaps.CustomOverlay({ map, position: pos, content: html })
      selectedMarkers.push(overlay)
    }
  })
}
// selectedPlacesByDay[day] 가 바뀔 때 (deep)
watch(
  () => selectedPlacesByDay[placeDay.value],
  () => {
    clearMarkers()
    addMarkers(places.value)
    drawSelectedMarkers()
  },
  { deep: true },
)

// 1) 현재 선택된 장소 ID를 빠르게 조회할 수 있도록 computed 추가
const selectedIds = computed(() => {
  const list = selectedPlacesByDay[placeDay.value] || []
  return new Set(list.map((p) => p.no))
})

// 2) addMarkers 함수 수정: 기본 마커 찍기 전 필터링
function addMarkers(newPlaces) {
  const kmaps = window.kakao.maps
  newPlaces.forEach((p) => {
    // 만약 이 place가 현재 선택된 목록에 있다면 기본 마커는 건너뛴다
    if (selectedIds.value.has(p.no)) return

    const marker = new kmaps.Marker({
      map,
      position: new kmaps.LatLng(p.latitude, p.longitude),
    })
    kmaps.event.addListener(marker, 'click', () => showPlaceDetailModal(p))
    marker.placeId = p.id
    baseMarkers.push(marker)
  })
}

// ④ places 초기 로딩 직후와, fetchPlaces 직후에 한 번 그려주기
onMounted(() => {
  const regionMap = {
    0: { lat: 36.5, lng: 127.8, level: 12 }, // 전국
    1: { lat: 37.5665, lng: 126.978, level: 8 }, // 서울특별시
    2: { lat: 37.4563, lng: 126.7052, level: 8 }, // 인천광역시
    3: { lat: 36.3504, lng: 127.3845, level: 8 }, // 대전광역시
    4: { lat: 35.8722, lng: 128.6025, level: 8 }, // 대구광역시
    5: { lat: 35.1595, lng: 126.8526, level: 8 }, // 광주광역시
    6: { lat: 35.1796, lng: 129.0756, level: 8 }, // 부산광역시
    7: { lat: 35.5384, lng: 129.3114, level: 8 }, // 울산광역시
    8: { lat: 36.4801, lng: 127.289, level: 9 }, // 세종특별자치시
    31: { lat: 37.4138, lng: 127, level: 10 }, // 경기도
    32: { lat: 37.8228, lng: 128.1555, level: 10 }, // 강원도
    33: { lat: 36.6285, lng: 127.9296, level: 10 }, // 충청북도
    34: { lat: 36.5184, lng: 126.8, level: 10 }, // 충청남도
    35: { lat: 36.4919, lng: 128.8889, level: 10 }, // 경상북도
    36: { lat: 35.4606, lng: 128.2132, level: 10 }, // 경상남도
    37: { lat: 35.7175, lng: 127.153, level: 10 }, // 전라북도
    38: { lat: 34.8161, lng: 126.462, level: 10 }, // 전라남도
    39: { lat: 33.3996, lng: 126.5312, level: 9 }, // 제주특별자치도
  }
  window.kakao.maps.load(() => {
    map = new window.kakao.maps.Map(mapContainer.value, {
      center: new window.kakao.maps.LatLng(regionMap[0].lat, regionMap[0].lng),
      level: regionMap[0].level,
    })
    addMarkers(places.value)
  })
  watch(selectedRegion, (newCode) => {
    const info = regionMap[newCode]
    if (map && info) {
      map.setCenter(new kakao.maps.LatLng(info.lat, info.lng))
      map.setLevel(info.level)
    }
  })
})

// ⑤ places가 바뀔 때마다 renderMarkers() 호출
watch(places, () => {
  addMarkers(places.value)
  drawSelectedMarkers()
})

function onSidebarPlaceClick(place) {
  // 2. 글로벌 map 객체를 place 좌표로 이동 & 줌 레벨 높임
  if (map && place.latitude != null && place.longitude != null) {
    const kmaps = window.kakao.maps
    const latLng = new kmaps.LatLng(place.latitude, place.longitude)
    map.setCenter(latLng)
  }

  // 3. 모달 열기
  selectedPlace.value = place
  showPlaceDetail.value = true
}

const currentDay = computed(() => {
  return activeNav.value === 'places' ? placeDay.value : planDay.value
})

watch(
  [currentDay, places],
  () => {
    clearMarkers()
    addMarkers(places.value)
    drawSelectedMarkers()
  },
  { deep: true },
)
// 언마운트 시 제거할 핸들러 참조
function onBeforeUnload(e) {
  e.preventDefault()
  // Chrome에서는 returnValue 설정이 필요합니다.
  e.returnValue = ''
}

onMounted(() => {
  window.addEventListener('beforeunload', onBeforeUnload)
})
onUnmounted(() => {
  window.removeEventListener('beforeunload', onBeforeUnload)
})
// 저장되지 않은 변경이 있는지 확인하는 헬퍼
function confirmDiscard() {
  return window.confirm('변경사항이 저장되지 않았습니다. 이대로 나가시겠습니까?')
}

// 라우터 네비게이션 가드
onBeforeRouteLeave((to, from, next) => {
  if (isSaveClicked.value) {
    next()
    return
  }
  if (confirmDiscard()) {
    next()
  } else {
    next(false)
  }
})
</script>

<style scoped>
/* 전부 Tailwind CSS로 스타일링 했습니다. */
/* helper(드래그 중) */
.drag-no-travel .travel-block {
  visibility: hidden !important;
}

/* 리스트 컨테이너에 is-dragging */
.is-dragging .travel-block {
  visibility: hidden;
}
.plan-item,
.travel-block {
  user-select: none; /* 텍스트 드래그 선택 방지 */
  -webkit-user-select: none;
}

/* 혹은 선택되더라도 배경 투명 처리 */
::selection {
  background: transparent;
}

/* slide-right transition */
.slide-right-enter-from {
  transform: translateX(-100%);
  opacity: 0;
}
.slide-right-enter-active {
  transition: all 0.3s ease;
}
.slide-right-enter-to {
  transform: translateX(0);
  opacity: 1;
}
.slide-right-leave-from {
  opacity: 1;
}
.slide-right-leave-active {
  transition: opacity 0.2s ease;
}
.slide-right-leave-to {
  opacity: 0;
}
/* appear 애니메이션도 enter 애니메이션과 동일하게 동작하도록 세팅 */
.slide-right-appear-from {
  transform: translateX(-100%);
  opacity: 0;
}
.slide-right-appear-active {
  transition: all 0.3s ease;
}
.slide-right-appear-to {
  transform: translateX(0);
  opacity: 1;
}
</style>
