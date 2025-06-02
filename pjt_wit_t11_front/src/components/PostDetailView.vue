<template>
  <div class="min-h-screen flex flex-col">
    <!-- 네비게이션 바 -->
    <HeaderNavBar />

    <!-- 게시글 상세 컨텐츠 -->
    <div class="flex-1 flex items-center justify-center px-4 py-8">
      <div class="max-w-4xl mx-auto">
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
          목록으로
        </button>

        <!-- 게시글 카드 -->
        <div class="bg-white rounded-lg shadow-md overflow-hidden">
          <!-- 게시글 헤더 -->
          <div class="p-6 border-b border-gray-200">
            <div class="flex items-center justify-between mb-2">
              <span
                class="inline-block px-3 py-1 text-sm font-medium rounded-full"
                :class="getCategoryClass(post.category)"
              >
                {{ getCategoryName(post.category) }}
              </span>
              <div class="text-sm text-gray-500">
                {{ formatDate(post.createdAt) }} · 조회 {{ post.views }}
              </div>
            </div>
            <h1 class="text-2xl font-bold text-gray-900 mb-3">{{ post.title }}</h1>
            <div class="flex items-center">
              <div class="flex-shrink-0">
                <img :src="post.authorAvatar" alt="작성자 프로필" class="h-10 w-10 rounded-full" />
              </div>
              <div class="ml-3">
                <p class="text-sm font-medium text-gray-900">{{ post.author }}</p>
              </div>
            </div>
          </div>

          <!-- 게시글 본문 -->
          <div class="p-6 prose max-w-none">
            <div v-html="post.content"></div>

            <!-- 이미지 갤러리 -->
            <div v-if="post.images && post.images.length > 0" class="mt-6 grid grid-cols-2 gap-4">
              <div
                v-for="(image, index) in post.images"
                :key="index"
                class="relative rounded-lg overflow-hidden h-64 cursor-pointer"
                @click="openImageModal(index)"
              >
                <img
                  :src="image"
                  alt="게시글 이미지"
                  class="w-full h-full object-cover transition-transform duration-300 hover:scale-105"
                />
              </div>
            </div>
          </div>

          <!-- 좋아요, 공유 버튼 -->
          <div class="px-6 py-4 border-t border-gray-200 flex items-center space-x-4">
            <button
              @click="toggleLike"
              class="flex items-center space-x-1 text-gray-500 hover:text-[#3c64b1] transition-colors"
              :class="{ 'text-[#3c64b1]': isLiked }"
            >
              <svg
                class="w-6 h-6"
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 20 20"
                fill="currentColor"
              >
                <path
                  d="M2 10.5a1.5 1.5 0 113 0v6a1.5 1.5 0 01-3 0v-6zM6 10.333v5.43a2 2 0 001.106 1.79l.05.025A4 4 0 008.943 18h5.416a2 2 0 001.962-1.608l1.2-6A2 2 0 0015.56 8H12V4a2 2 0 00-2-2 1 1 0 00-1 1v.667a4 4 0 01-.8 2.4L6.8 7.933a4 4 0 00-.8 2.4z"
                />
              </svg>
              <span>좋아요 {{ likeCount }}</span>
            </button>
            <button
              class="flex items-center space-x-1 text-gray-500 hover:text-[#3c64b1] transition-colors"
            >
              <svg
                class="w-6 h-6"
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 20 20"
                fill="currentColor"
              >
                <path
                  d="M15 8a3 3 0 10-2.977-2.63l-4.94 2.47a3 3 0 100 4.319l4.94 2.47a3 3 0 10.895-1.789l-4.94-2.47a3.027 3.027 0 000-.74l4.94-2.47C13.456 7.68 14.19 8 15 8z"
                />
              </svg>
              <span>공유하기</span>
            </button>
          </div>
        </div>

        <!-- 댓글 섹션 -->
        <div class="mt-8 bg-white rounded-lg shadow-md overflow-hidden">
          <div class="p-6 border-b border-gray-200">
            <h2 class="text-xl font-bold text-gray-900">댓글 {{ comments.length }}</h2>
          </div>

          <!-- 댓글 작성 폼 -->
          <div class="p-6 border-b border-gray-200">
            <div class="flex space-x-4">
              <div class="flex-shrink-0">
                <img
                  src="https://placehold.co/100x100?text=User"
                  alt="사용자 프로필"
                  class="h-10 w-10 rounded-full"
                />
              </div>
              <div class="flex-1">
                <textarea
                  v-model="newComment"
                  rows="3"
                  class="w-full border border-gray-300 rounded-md p-3 focus:outline-none focus:ring-2 focus:ring-[#3c64b1]"
                  placeholder="댓글을 작성해주세요..."
                ></textarea>
                <div class="mt-2 flex justify-end">
                  <button
                    @click="addComment"
                    class="px-4 py-2 bg-[#3c64b1] text-white rounded-md hover:bg-[#2a4580] transition-colors"
                  >
                    댓글 작성
                  </button>
                </div>
              </div>
            </div>
          </div>

          <!-- 댓글 목록 -->
          <div v-if="comments.length > 0">
            <div v-for="comment in comments" :key="comment.id" class="p-6 border-b border-gray-200">
              <div class="flex space-x-4">
                <div class="flex-shrink-0">
                  <img
                    :src="comment.authorAvatar"
                    alt="댓글 작성자 프로필"
                    class="h-10 w-10 rounded-full"
                  />
                </div>
                <div class="flex-1">
                  <div class="flex items-center justify-between">
                    <h3 class="text-sm font-medium text-gray-900">{{ comment.author }}</h3>
                    <p class="text-sm text-gray-500">{{ formatDate(comment.createdAt) }}</p>
                  </div>
                  <div class="mt-2 text-sm text-gray-700">
                    <p>{{ comment.content }}</p>
                  </div>
                  <div class="mt-2 flex items-center space-x-4">
                    <button
                      class="text-sm text-gray-500 hover:text-[#3c64b1] transition-colors"
                      @click="toggleCommentLike(comment)"
                    >
                      <span>좋아요 {{ comment.likes }}</span>
                    </button>
                    <button
                      class="text-sm text-gray-500 hover:text-[#3c64b1] transition-colors"
                      @click="replyToComment(comment)"
                    >
                      <span>답글</span>
                    </button>
                  </div>

                  <!-- 대댓글 -->
                  <div
                    v-if="comment.replies && comment.replies.length > 0"
                    class="mt-4 pl-4 border-l-2 border-gray-200"
                  >
                    <div v-for="reply in comment.replies" :key="reply.id" class="mt-4">
                      <div class="flex space-x-4">
                        <div class="flex-shrink-0">
                          <img
                            :src="reply.authorAvatar"
                            alt="답글 작성자 프로필"
                            class="h-8 w-8 rounded-full"
                          />
                        </div>
                        <div class="flex-1">
                          <div class="flex items-center justify-between">
                            <h3 class="text-sm font-medium text-gray-900">{{ reply.author }}</h3>
                            <p class="text-sm text-gray-500">{{ formatDate(reply.createdAt) }}</p>
                          </div>
                          <div class="mt-1 text-sm text-gray-700">
                            <p>{{ reply.content }}</p>
                          </div>
                          <div class="mt-1">
                            <button
                              class="text-xs text-gray-500 hover:text-[#3c64b1] transition-colors"
                            >
                              <span>좋아요 {{ reply.likes }}</span>
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>

                  <!-- 답글 작성 폼 -->
                  <div
                    v-if="replyingTo === comment.id"
                    class="mt-4 pl-4 border-l-2 border-gray-200"
                  >
                    <div class="flex space-x-4">
                      <div class="flex-shrink-0">
                        <img
                          src="https://placehold.co/100x100?text=User"
                          alt="사용자 프로필"
                          class="h-8 w-8 rounded-full"
                        />
                      </div>
                      <div class="flex-1">
                        <textarea
                          v-model="replyContent"
                          rows="2"
                          class="w-full border border-gray-300 rounded-md p-2 text-sm focus:outline-none focus:ring-2 focus:ring-[#3c64b1]"
                          placeholder="답글을 작성해주세요..."
                        ></textarea>
                        <div class="mt-2 flex justify-end space-x-2">
                          <button
                            @click="cancelReply"
                            class="px-3 py-1 text-sm text-gray-600 hover:text-gray-900 transition-colors"
                          >
                            취소
                          </button>
                          <button
                            @click="submitReply"
                            class="px-3 py-1 text-sm bg-[#3c64b1] text-white rounded-md hover:bg-[#2a4580] transition-colors"
                          >
                            답글 작성
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 댓글 없음 -->
          <div v-else class="p-6 text-center text-gray-500">
            <p>첫 번째 댓글을 작성해보세요.</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import HeaderNavBar from '@/components/HeaderNavBar.vue'

const router = useRouter()
const route = useRoute()
const postId = route.params.id

// 게시글 상세 정보
const post = ref({
  id: postId,
  title: '제주도 3박 4일 여행 후기 - 숨은 명소 추천',
  author: '제주사랑',
  authorAvatar: 'https://placehold.co/100x100?text=제주사랑',
  views: 1245,
  createdAt: '2024-05-15T09:30:00',
  category: 'travel-story',
  content: `
    <p>안녕하세요, 여러분! 지난주에 다녀온 제주도 3박 4일 여행 후기를 공유합니다.</p>
    <p>이번 여행은 렌트카를 빌려서 제주도의 숨은 명소들을 중심으로 다녀왔는데요, 정말 좋았습니다!</p>
    <h3>1일차 - 제주 동부</h3>
    <p>첫날은 성산일출봉과 우도를 다녀왔습니다. 성산일출봉은 아침 일찍 가는 것을 추천드려요. 사람이 적고 경치가 정말 장관입니다.</p>
    <p>우도는 페리를 타고 30분 정도 가면 도착하는데, 우도 땅콩 아이스크림과 바다 뷰가 일품이었습니다.</p>
    <h3>2일차 - 제주 남부</h3>
    <p>둘째 날은 중문관광단지와 천지연폭포를 다녀왔습니다. 중문해변은 생각보다 한적해서 좋았고, 근처 카페들도 뷰가 좋았어요.</p>
    <h3>3일차 - 제주 서부</h3>
    <p>셋째 날은 협재해수욕장과 카멜리아힐을 방문했습니다. 협재해수욕장의 에메랄드빛 바다는 정말 환상적이었어요!</p>
    <h3>4일차 - 제주 북부</h3>
    <p>마지막 날은 만장굴과 김녕해수욕장을 다녀왔습니다. 만장굴은 시원해서 여름 여행에 딱이었어요.</p>
    <p>다음에 또 방문하게 된다면 이번에 못 가본 서귀포 쪽을 더 자세히 둘러보고 싶네요.</p>
    <p>질문 있으시면 댓글로 남겨주세요! 답변 드리겠습니다. 😊</p>
  `,
  images: [
    'https://placehold.co/800x600?text=제주도+성산일출봉',
    'https://placehold.co/800x600?text=우도+해변',
    'https://placehold.co/800x600?text=협재해수욕장',
    'https://placehold.co/800x600?text=카멜리아힐',
  ],
})

// 좋아요 상태
const isLiked = ref(false)
const likeCount = ref(42)

// 댓글 관련 상태
const comments = ref([
  {
    id: 1,
    author: '여행초보자',
    authorAvatar: 'https://placehold.co/100x100?text=여행초보자',
    content: '정말 유익한 정보 감사합니다! 우도는 당일치기로 가능한가요?',
    createdAt: '2024-05-15T10:15:00',
    likes: 5,
    replies: [
      {
        id: 3,
        author: '제주사랑',
        authorAvatar: 'https://placehold.co/100x100?text=제주사랑',
        content:
          '네, 우도는 당일치기로 충분히 가능합니다! 오전에 가서 오후에 돌아오는 일정으로 계획하시면 좋을 것 같아요.',
        createdAt: '2024-05-15T10:30:00',
        likes: 2,
      },
    ],
  },
  {
    id: 2,
    author: '맛집탐험가',
    authorAvatar: 'https://placehold.co/100x100?text=맛집탐험가',
    content: '혹시 제주도에서 가장 맛있었던 음식점이 어디인지 알 수 있을까요?',
    createdAt: '2024-05-15T11:45:00',
    likes: 3,
    replies: [],
  },
])

const newComment = ref('')
const replyingTo = ref(null)
const replyContent = ref('')

// 좋아요 토글
const toggleLike = () => {
  isLiked.value = !isLiked.value
  likeCount.value += isLiked.value ? 1 : -1
}

// 댓글 좋아요 토글
const toggleCommentLike = (comment) => {
  comment.likes += 1
}

// 댓글 작성
const addComment = () => {
  if (!newComment.value.trim()) return

  const comment = {
    id: comments.value.length + 1,
    author: '사용자',
    authorAvatar: 'https://placehold.co/100x100?text=User',
    content: newComment.value,
    createdAt: new Date().toISOString(),
    likes: 0,
    replies: [],
  }

  comments.value.unshift(comment)
  newComment.value = ''
}

// 답글 작성 모드 설정
const replyToComment = (comment) => {
  replyingTo.value = comment.id
  replyContent.value = ''
}

// 답글 작성 취소
const cancelReply = () => {
  replyingTo.value = null
  replyContent.value = ''
}

// 답글 제출
const submitReply = () => {
  if (!replyContent.value.trim() || replyingTo.value === null) return

  const reply = {
    id: Date.now(),
    author: '사용자',
    authorAvatar: 'https://placehold.co/100x100?text=User',
    content: replyContent.value,
    createdAt: new Date().toISOString(),
    likes: 0,
  }

  const commentIndex = comments.value.findIndex((c) => c.id === replyingTo.value)
  if (commentIndex !== -1) {
    if (!comments.value[commentIndex].replies) {
      comments.value[commentIndex].replies = []
    }
    comments.value[commentIndex].replies.push(reply)
  }

  replyingTo.value = null
  replyContent.value = ''
}

// 이미지 모달
const openImageModal = (index) => {
  // 이미지 모달 구현 (실제 구현에서는 이미지 갤러리 모달 추가)
  console.log('이미지 모달 열기:', index)
}

// 뒤로가기
const goBack = () => {
  router.push({ name: 'Board' })
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

// 컴포넌트 마운트 시 데이터 로드
onMounted(() => {
  // 실제 구현에서는 API 호출로 게시글 상세 정보 가져오기
  console.log('게시글 ID:', postId)
})
</script>

<style scoped>
/* 추가 스타일이 필요한 경우 여기에 작성 */
.prose {
  max-width: 100%;
  color: #374151;
  line-height: 1.75;
}

.prose h3 {
  font-weight: 600;
  font-size: 1.25rem;
  margin-top: 1.5rem;
  margin-bottom: 0.5rem;
  color: #111827;
}

.prose p {
  margin-top: 0.75rem;
  margin-bottom: 0.75rem;
}
</style>
