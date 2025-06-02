<template>
  <div class="min-h-screen flex flex-col bg-[#f6f8ff]">
    <!-- 네비게이션 바 -->
    <HeaderNavBar />

    <!-- 게시판 컨텐츠 -->
    <div class="container mx-auto px-4 py-8 flex-1">
      <div class="max-w-5xl mx-auto">
        <!-- 게시판 헤더 -->
        <div class="mb-8">
          <h1 class="text-3xl font-bold text-gray-800">여행 이야기</h1>
          <p class="text-gray-600 mt-2">다양한 여행 경험과 정보를 공유해보세요</p>
        </div>

        <!-- 검색 및 필터 영역 -->
        <div class="bg-white rounded-lg shadow-md p-4 mb-6">
          <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
            <!-- 카테고리 필터 -->
            <div class="flex items-center space-x-2">
              <span class="text-sm text-gray-600">카테고리:</span>
              <select
                v-model="selectedCategory"
                class="border border-gray-300 rounded-md px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-[#3c64b1]"
              >
                <option value="">전체</option>
                <option value="travel-story">여행 후기</option>
                <option value="travel-tip">여행 팁</option>
                <option value="travel-question">질문</option>
                <option value="travel-companion">동행 찾기</option>
              </select>
            </div>

            <!-- 검색 -->
            <div class="relative flex-1 max-w-md">
              <input
                type="text"
                v-model="searchQuery"
                placeholder="검색어를 입력하세요"
                class="w-full border border-gray-300 rounded-md pl-10 pr-4 py-2 focus:outline-none focus:ring-2 focus:ring-[#3c64b1]"
                @keyup.enter="handleSearch"
              />
              <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <svg
                  class="h-5 w-5 text-gray-400"
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 20 20"
                  fill="currentColor"
                  aria-hidden="true"
                >
                  <path
                    fill-rule="evenodd"
                    d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                    clip-rule="evenodd"
                  />
                </svg>
              </div>
            </div>

            <!-- 글쓰기 버튼 -->
            <button
              @click="goToCreatePost"
              class="bg-[#3c64b1] text-white px-4 py-2 rounded-md hover:bg-[#2a4580] transition duration-200 flex items-center justify-center"
            >
              <svg
                class="w-5 h-5 mr-1"
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 20 20"
                fill="currentColor"
              >
                <path
                  fill-rule="evenodd"
                  d="M10 3a1 1 0 011 1v5h5a1 1 0 110 2h-5v5a1 1 0 11-2 0v-5H4a1 1 0 110-2h5V4a1 1 0 011-1z"
                  clip-rule="evenodd"
                />
              </svg>
              글쓰기
            </button>
          </div>
        </div>

        <!-- 게시글 목록 -->
        <div class="bg-white rounded-lg shadow-md overflow-hidden">
          <!-- 게시글 헤더 -->
          <div class="hidden md:grid md:grid-cols-12 bg-gray-50 px-6 py-3 border-b border-gray-200">
            <div class="col-span-1 font-medium text-gray-500 text-sm">번호</div>
            <div class="col-span-2 font-medium text-gray-500 text-sm">카테고리</div>
            <div class="col-span-5 font-medium text-gray-500 text-sm">제목</div>
            <div class="col-span-2 font-medium text-gray-500 text-sm">작성자</div>
            <div class="col-span-1 font-medium text-gray-500 text-sm">조회</div>
            <div class="col-span-1 font-medium text-gray-500 text-sm">날짜</div>
          </div>

          <!-- 게시글 아이템 -->
          <div v-if="posts.length > 0">
            <div
              v-for="post in posts"
              :key="post.id"
              class="border-b border-gray-200 hover:bg-gray-50 transition-colors duration-150"
              @click="goToPostDetail(post.id)"
            >
              <!-- 모바일 뷰 -->
              <div class="block md:hidden p-4">
                <div class="flex justify-between items-start mb-2">
                  <span
                    class="inline-block px-2 py-1 text-xs font-medium rounded-full"
                    :class="getCategoryClass(post.category)"
                  >
                    {{ getCategoryName(post.category) }}
                  </span>
                  <span class="text-xs text-gray-500">{{ formatDate(post.createdAt) }}</span>
                </div>
                <h3 class="font-medium mb-1 truncate">{{ post.title }}</h3>
                <div class="flex justify-between items-center text-sm">
                  <span class="text-gray-600">{{ post.author }}</span>
                  <span class="text-gray-500">조회 {{ post.views }}</span>
                </div>
              </div>

              <!-- 데스크톱 뷰 -->
              <div class="hidden md:grid md:grid-cols-12 px-6 py-4 items-center cursor-pointer">
                <div class="col-span-1 text-gray-500 text-sm">{{ post.id }}</div>
                <div class="col-span-2">
                  <span
                    class="inline-block px-2 py-1 text-xs font-medium rounded-full"
                    :class="getCategoryClass(post.category)"
                  >
                    {{ getCategoryName(post.category) }}
                  </span>
                </div>
                <div class="col-span-5 font-medium truncate">
                  {{ post.title }}
                  <span v-if="post.commentCount > 0" class="text-[#3c64b1] ml-2">
                    [{{ post.commentCount }}]
                  </span>
                </div>
                <div class="col-span-2 text-gray-600 text-sm truncate">{{ post.author }}</div>
                <div class="col-span-1 text-gray-500 text-sm">{{ post.views }}</div>
                <div class="col-span-1 text-gray-500 text-sm">{{ formatDate(post.createdAt) }}</div>
              </div>
            </div>
          </div>

          <!-- 게시글 없음 -->
          <div v-else class="py-16 text-center text-gray-500">
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
                d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
              />
            </svg>
            <h3 class="mt-2 text-sm font-medium text-gray-900">게시글이 없습니다</h3>
            <p class="mt-1 text-sm text-gray-500">첫 번째 게시글을 작성해보세요.</p>
            <div class="mt-6">
              <button
                @click="goToCreatePost"
                class="inline-flex items-center px-4 py-2 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-[#3c64b1] hover:bg-[#2a4580] focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-[#3c64b1]"
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
                새 게시글 작성
              </button>
            </div>
          </div>
        </div>

        <!-- 커서 기반 페이지네이션 -->
        <div v-if="posts.length > 0 && hasMore" class="mt-6 flex justify-center">
          <button
            @click="loadMore"
            class="px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-[#3c64b1]"
            :disabled="isLoading"
          >
            <span v-if="isLoading">
              <svg
                class="animate-spin -ml-1 mr-2 h-4 w-4 text-gray-700 inline"
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
              로딩 중...
            </span>
            <span v-else>더 보기</span>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watch } from 'vue'
import { useRouter } from 'vue-router'
import HeaderNavBar from '@/components/HeaderNavBar.vue'

const router = useRouter()
const posts = ref([])
const cursor = ref(null)
const hasMore = ref(true)
const isLoading = ref(false)
const searchQuery = ref('')
const selectedCategory = ref('')

// 게시글 데이터 가져오기 (실제로는 API 호출)
const fetchPosts = async (newCursor = null, category = '', query = '') => {
  isLoading.value = true

  try {
    // 실제 구현에서는 API 호출로 대체
    // const response = await api.get('/posts', {
    //   params: { cursor: newCursor, category, query, limit: 10 }
    // })

    // 더미 데이터로 API 응답 시뮬레이션
    await new Promise((resolve) => setTimeout(resolve, 800)) // 로딩 시뮬레이션

    const dummyPosts = generateDummyPosts(newCursor, category, query)

    if (newCursor === null) {
      // 첫 로드 또는 필터 변경 시 목록 초기화
      posts.value = dummyPosts.data
    } else {
      // 더 보기 시 목록에 추가
      posts.value = [...posts.value, ...dummyPosts.data]
    }

    cursor.value = dummyPosts.nextCursor
    hasMore.value = dummyPosts.hasMore
  } catch (error) {
    console.error('게시글을 불러오는 중 오류가 발생했습니다:', error)
  } finally {
    isLoading.value = false
  }
}

// 더미 데이터 생성 함수 (실제 구현에서는 API 호출로 대체)
const generateDummyPosts = (currentCursor, category, query) => {
  const allPosts = [
    {
      id: 100,
      title: '제주도 3박 4일 여행 후기 - 숨은 명소 추천',
      author: '제주사랑',
      views: 1245,
      createdAt: '2024-05-15T09:30:00',
      category: 'travel-story',
      commentCount: 23,
    },
    {
      id: 99,
      title: '유럽 배낭여행 준비물 체크리스트',
      author: '세계여행자',
      views: 982,
      createdAt: '2024-05-14T14:22:00',
      category: 'travel-tip',
      commentCount: 15,
    },
    {
      id: 98,
      title: '부산 해운대 근처 맛집 추천해주세요!',
      author: '맛집탐험가',
      views: 756,
      createdAt: '2024-05-14T11:15:00',
      category: 'travel-question',
      commentCount: 8,
    },
    {
      id: 97,
      title: '6월 초 강원도 여행 동행 구합니다',
      author: '산과바다',
      views: 432,
      createdAt: '2024-05-13T16:40:00',
      category: 'travel-companion',
      commentCount: 5,
    },
    {
      id: 96,
      title: '일본 오사카 3박 4일 여행 경비 정리',
      author: '여행가계부',
      views: 1102,
      createdAt: '2024-05-12T08:50:00',
      category: 'travel-tip',
      commentCount: 12,
    },
    {
      id: 95,
      title: '태국 방콕 여행 후기 및 꿀팁',
      author: '동남아전문가',
      views: 876,
      createdAt: '2024-05-11T19:20:00',
      category: 'travel-story',
      commentCount: 9,
    },
    {
      id: 94,
      title: '혼자 떠나는 여행, 안전하게 즐기는 방법',
      author: '솔로트래블러',
      views: 1543,
      createdAt: '2024-05-10T12:10:00',
      category: 'travel-tip',
      commentCount: 31,
    },
    {
      id: 93,
      title: '경주 1박 2일 코스 추천해주세요',
      author: '역사탐방',
      views: 421,
      createdAt: '2024-05-09T15:30:00',
      category: 'travel-question',
      commentCount: 7,
    },
    {
      id: 92,
      title: '여름 휴가 제주도 vs 강원도 어디가 좋을까요?',
      author: '여름휴가고민',
      views: 689,
      createdAt: '2024-05-08T10:45:00',
      category: 'travel-question',
      commentCount: 14,
    },
    {
      id: 91,
      title: '7월 유럽 배낭여행 동행 3명 구합니다',
      author: '유럽여행러',
      views: 532,
      createdAt: '2024-05-07T17:25:00',
      category: 'travel-companion',
      commentCount: 11,
    },
    {
      id: 90,
      title: '베트남 다낭 5일 여행 후기',
      author: '베트남러버',
      views: 921,
      createdAt: '2024-05-06T09:15:00',
      category: 'travel-story',
      commentCount: 18,
    },
    {
      id: 89,
      title: '해외여행 필수 앱 추천',
      author: '앱마스터',
      views: 1876,
      createdAt: '2024-05-05T14:50:00',
      category: 'travel-tip',
      commentCount: 27,
    },
    {
      id: 88,
      title: '제주도 렌트카 vs 대중교통, 어떤 게 좋을까요?',
      author: '제주여행초보',
      views: 743,
      createdAt: '2024-05-04T11:30:00',
      category: 'travel-question',
      commentCount: 19,
    },
    {
      id: 87,
      title: '부산 해운대 근처 게스트하우스 추천',
      author: '부산사랑',
      views: 512,
      createdAt: '2024-05-03T16:20:00',
      category: 'travel-tip',
      commentCount: 6,
    },
    {
      id: 86,
      title: '5월 말 제주도 동행 구해요',
      author: '봄여행자',
      views: 398,
      createdAt: '2024-05-02T13:40:00',
      category: 'travel-companion',
      commentCount: 4,
    },
  ]

  // 필터링
  let filteredPosts = [...allPosts]

  if (category) {
    filteredPosts = filteredPosts.filter((post) => post.category === category)
  }

  if (query) {
    const lowerQuery = query.toLowerCase()
    filteredPosts = filteredPosts.filter(
      (post) =>
        post.title.toLowerCase().includes(lowerQuery) ||
        post.author.toLowerCase().includes(lowerQuery),
    )
  }

  // 커서 기반 페이지네이션
  let startIndex = 0
  if (currentCursor) {
    const cursorIndex = filteredPosts.findIndex((post) => post.id === parseInt(currentCursor))
    if (cursorIndex !== -1) {
      startIndex = cursorIndex + 1
    }
  }

  // 5개씩 가져오기
  const pageSize = 5
  const endIndex = Math.min(startIndex + pageSize, filteredPosts.length)
  const paginatedPosts = filteredPosts.slice(startIndex, endIndex)

  // 다음 커서와 더 있는지 여부 결정
  const nextCursor = endIndex < filteredPosts.length ? filteredPosts[endIndex - 1].id : null
  const hasMore = endIndex < filteredPosts.length

  return {
    data: paginatedPosts,
    nextCursor,
    hasMore,
  }
}

// 더 보기 버튼 클릭 핸들러
const loadMore = () => {
  if (!isLoading.value && hasMore.value) {
    fetchPosts(cursor.value, selectedCategory.value, searchQuery.value)
  }
}

// 검색 핸들러
const handleSearch = () => {
  cursor.value = null // 커서 초기화
  fetchPosts(null, selectedCategory.value, searchQuery.value)
}

// 카테고리 클래스 반환
const getCategoryClass = (category) => {
  switch (category) {
    case 'travel-story':
      return 'bg-blue-100 text-blue-800'
    case 'travel-tip':
      return 'bg-green-100 text-green-800'
    case 'travel-question':
      return 'bg-yellow-100 text-yellow-800'
    case 'travel-companion':
      return 'bg-purple-100 text-purple-800'
    default:
      return 'bg-gray-100 text-gray-800'
  }
}

// 카테고리 이름 반환
const getCategoryName = (category) => {
  switch (category) {
    case 'travel-story':
      return '여행 후기'
    case 'travel-tip':
      return '여행 팁'
    case 'travel-question':
      return '질문'
    case 'travel-companion':
      return '동행 찾기'
    default:
      return '기타'
  }
}

// 날짜 포맷팅
const formatDate = (dateString) => {
  const date = new Date(dateString)
  const now = new Date()

  // 오늘 날짜인 경우 시간만 표시
  if (date.toDateString() === now.toDateString()) {
    return `${String(date.getHours()).padStart(2, '0')}:${String(date.getMinutes()).padStart(2, '0')}`
  }

  // 올해 날짜인 경우 월-일 표시
  if (date.getFullYear() === now.getFullYear()) {
    return `${date.getMonth() + 1}월 ${date.getDate()}일`
  }

  // 그 외에는 연-월-일 표시
  return `${date.getFullYear()}.${String(date.getMonth() + 1).padStart(2, '0')}.${String(date.getDate()).padStart(2, '0')}`
}

// 게시글 상세 페이지로 이동
const goToPostDetail = (postId) => {
  router.push({ name: 'PostDetail', params: { id: postId } })
}

// 게시글 작성 페이지로 이동
const goToCreatePost = () => {
  router.push({ name: 'PostCreate' })
}

// 컴포넌트 마운트 시 게시글 로드
onMounted(() => {
  fetchPosts()
})

// 카테고리 변경 감지
watch(selectedCategory, () => {
  cursor.value = null // 커서 초기화
  fetchPosts(null, selectedCategory.value, searchQuery.value)
})

// 검색어 변경 감지
watch(searchQuery, () => {
  cursor.value = null // 커서 초기화
  fetchPosts(null, selectedCategory.value, searchQuery.value)
})
</script>

<style scoped>
/* 추가 스타일이 필요한 경우 여기에 작성 */
</style>
