<template>
  <div class="min-h-screen flex flex-col bg-[#f6f8ff]">
    <!-- 네비게이션 바 -->
    <HeaderNavBar />

    <!-- 게시글 작성 컨텐츠 -->
    <div class="container mx-auto px-4 py-8 flex-1">
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

        <!-- 게시글 작성 폼 -->
        <div class="bg-white rounded-lg shadow-md overflow-hidden">
          <div class="p-6">
            <h1 class="text-2xl font-bold text-gray-900 mb-6">게시글 작성</h1>

            <form @submit.prevent="submitPost" class="space-y-6">
              <!-- 카테고리 선택 -->
              <div>
                <label for="category" class="block text-sm font-medium text-gray-700 mb-1"
                  >카테고리</label
                >
                <select
                  id="category"
                  v-model="postData.category"
                  required
                  class="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-[#3c64b1]"
                >
                  <option value="" disabled>카테고리를 선택하세요</option>
                  <option value="travel-story">여행 후기</option>
                  <option value="travel-tip">여행 팁</option>
                  <option value="travel-question">질문</option>
                  <option value="travel-companion">동행 찾기</option>
                </select>
              </div>

              <!-- 제목 입력 -->
              <div>
                <label for="title" class="block text-sm font-medium text-gray-700 mb-1">제목</label>
                <input
                  id="title"
                  v-model="postData.title"
                  type="text"
                  required
                  class="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-[#3c64b1]"
                  placeholder="제목을 입력하세요"
                />
              </div>

              <!-- 본문 에디터 -->
              <div>
                <label for="content" class="block text-sm font-medium text-gray-700 mb-1"
                  >내용</label
                >
                <div class="border border-gray-300 rounded-md overflow-hidden">
                  <!-- 에디터 툴바 -->
                  <div class="flex items-center space-x-1 bg-gray-50 p-2 border-b border-gray-300">
                    <button
                      type="button"
                      @click="formatText('bold')"
                      class="p-1 rounded hover:bg-gray-200"
                      title="굵게"
                    >
                      <svg
                        class="w-5 h-5 text-gray-700"
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="currentColor"
                        stroke-width="2"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      >
                        <path d="M6 4h8a4 4 0 0 1 4 4 4 4 0 0 1-4 4H6z"></path>
                        <path d="M6 12h9a4 4 0 0 1 4 4 4 4 0 0 1-4 4H6z"></path>
                      </svg>
                    </button>
                    <button
                      type="button"
                      @click="formatText('italic')"
                      class="p-1 rounded hover:bg-gray-200"
                      title="기울임"
                    >
                      <svg
                        class="w-5 h-5 text-gray-700"
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="currentColor"
                        stroke-width="2"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      >
                        <line x1="19" y1="4" x2="10" y2="4"></line>
                        <line x1="14" y1="20" x2="5" y2="20"></line>
                        <line x1="15" y1="4" x2="9" y2="20"></line>
                      </svg>
                    </button>
                    <button
                      type="button"
                      @click="formatText('underline')"
                      class="p-1 rounded hover:bg-gray-200"
                      title="밑줄"
                    >
                      <svg
                        class="w-5 h-5 text-gray-700"
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="currentColor"
                        stroke-width="2"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      >
                        <path d="M6 3v7a6 6 0 0 0 6 6 6 6 0 0 0 6-6V3"></path>
                        <line x1="4" y1="21" x2="20" y2="21"></line>
                      </svg>
                    </button>
                    <div class="h-6 border-l border-gray-300 mx-1"></div>
                    <button
                      type="button"
                      @click="formatText('h2')"
                      class="p-1 rounded hover:bg-gray-200"
                      title="제목"
                    >
                      <svg
                        class="w-5 h-5 text-gray-700"
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="currentColor"
                        stroke-width="2"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      >
                        <path d="M4 12h8"></path>
                        <path d="M4 18V6"></path>
                        <path d="M12 18V6"></path>
                        <path d="M16 12h4"></path>
                        <path d="M16 18V6"></path>
                        <path d="M20 18V6"></path>
                      </svg>
                    </button>
                    <button
                      type="button"
                      @click="formatText('h3')"
                      class="p-1 rounded hover:bg-gray-200"
                      title="소제목"
                    >
                      <svg
                        class="w-5 h-5 text-gray-700"
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="currentColor"
                        stroke-width="2"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      >
                        <path d="M4 12h8"></path>
                        <path d="M4 18V6"></path>
                        <path d="M12 18V6"></path>
                        <path d="M17 10h-2"></path>
                        <path d="M19 6h-4v12h4"></path>
                        <path d="M19 10h-4"></path>
                      </svg>
                    </button>
                    <div class="h-6 border-l border-gray-300 mx-1"></div>
                    <button
                      type="button"
                      @click="addImage"
                      class="p-1 rounded hover:bg-gray-200"
                      title="이미지 추가"
                    >
                      <svg
                        class="w-5 h-5 text-gray-700"
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="currentColor"
                        stroke-width="2"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      >
                        <rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
                        <circle cx="8.5" cy="8.5" r="1.5"></circle>
                        <polyline points="21 15 16 10 5 21"></polyline>
                      </svg>
                    </button>
                  </div>

                  <!-- 에디터 본문 -->
                  <textarea
                    id="content"
                    v-model="postData.content"
                    rows="12"
                    required
                    class="w-full p-3 focus:outline-none"
                    placeholder="내용을 입력하세요..."
                  ></textarea>
                </div>
              </div>

              <!-- 이미지 업로드 -->
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">이미지 첨부</label>
                <div
                  class="mt-1 flex justify-center px-6 pt-5 pb-6 border-2 border-gray-300 border-dashed rounded-md"
                >
                  <div class="space-y-1 text-center">
                    <svg
                      class="mx-auto h-12 w-12 text-gray-400"
                      stroke="currentColor"
                      fill="none"
                      viewBox="0 0 48 48"
                      aria-hidden="true"
                    >
                      <path
                        d="M28 8H12a4 4 0 00-4 4v20m32-12v8m0 0v8a4 4 0 01-4 4H12a4 4 0 01-4-4v-4m32-4l-3.172-3.172a4 4 0 00-5.656 0L28 28M8 32l9.172-9.172a4 4 0 015.656 0L28 28m0 0l4 4m4-24h8m-4-4v8m-12 4h.02"
                        stroke-width="2"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      />
                    </svg>
                    <div class="flex text-sm text-gray-600">
                      <label
                        for="file-upload"
                        class="relative cursor-pointer bg-white rounded-md font-medium text-[#3c64b1] hover:text-[#2a4580] focus-within:outline-none"
                      >
                        <span>파일 업로드</span>
                        <input
                          id="file-upload"
                          name="file-upload"
                          type="file"
                          class="sr-only"
                          multiple
                          @change="handleFileUpload"
                          accept="image/*"
                        />
                      </label>
                      <p class="pl-1">또는 드래그 앤 드롭</p>
                    </div>
                    <p class="text-xs text-gray-500">PNG, JPG, GIF 최대 10MB</p>
                  </div>
                </div>
              </div>

              <!-- 업로드된 이미지 미리보기 -->
              <div v-if="uploadedImages.length > 0" class="grid grid-cols-3 gap-4">
                <div
                  v-for="(image, index) in uploadedImages"
                  :key="index"
                  class="relative rounded-md overflow-hidden h-32"
                >
                  <img
                    :src="image.preview"
                    alt="업로드 이미지"
                    class="w-full h-full object-cover"
                  />
                  <button
                    type="button"
                    @click="removeImage(index)"
                    class="absolute top-1 right-1 bg-red-500 text-white rounded-full p-1 hover:bg-red-600"
                  >
                    <svg
                      class="w-4 h-4"
                      xmlns="http://www.w3.org/2000/svg"
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

              <!-- 제출 버튼 -->
              <div class="flex justify-end">
                <button
                  type="button"
                  @click="goBack"
                  class="mr-4 px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none"
                >
                  취소
                </button>
                <button
                  type="submit"
                  class="px-4 py-2 bg-[#3c64b1] text-white rounded-md hover:bg-[#2a4580] transition-colors"
                >
                  게시하기
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import HeaderNavBar from '@/components/HeaderNavBar.vue'

const router = useRouter()

// 게시글 데이터
const postData = ref({
  title: '',
  content: '',
  category: '',
})

// 업로드된 이미지
const uploadedImages = ref([])

// 뒤로가기
const goBack = () => {
  router.push({ name: 'Board' })
}

// 텍스트 포맷팅
const formatText = (format) => {
  const textarea = document.getElementById('content')
  const start = textarea.selectionStart
  const end = textarea.selectionEnd
  const selectedText = postData.value.content.substring(start, end)
  let formattedText = ''

  switch (format) {
    case 'bold':
      formattedText = `**${selectedText}**`
      break
    case 'italic':
      formattedText = `*${selectedText}*`
      break
    case 'underline':
      formattedText = `<u>${selectedText}</u>`
      break
    case 'h2':
      formattedText = `\n## ${selectedText}\n`
      break
    case 'h3':
      formattedText = `\n### ${selectedText}\n`
      break
    default:
      formattedText = selectedText
  }

  postData.value.content =
    postData.value.content.substring(0, start) +
    formattedText +
    postData.value.content.substring(end)

  // 포커스 유지 및 커서 위치 조정
  setTimeout(() => {
    textarea.focus()
    textarea.setSelectionRange(start + formattedText.length, start + formattedText.length)
  }, 0)
}

// 이미지 추가 (텍스트 에디터에 이미지 마크다운 삽입)
const addImage = () => {
  const textarea = document.getElementById('content')
  const cursorPos = textarea.selectionStart
  const imageMarkdown = '![이미지 설명](이미지 URL)\n'

  postData.value.content =
    postData.value.content.substring(0, cursorPos) +
    imageMarkdown +
    postData.value.content.substring(cursorPos)

  // 포커스 유지 및 커서 위치 조정
  setTimeout(() => {
    textarea.focus()
    textarea.setSelectionRange(cursorPos + 2, cursorPos + 8) // "이미지 설명" 부분 선택
  }, 0)
}

// 파일 업로드 처리
const handleFileUpload = (event) => {
  const files = event.target.files

  if (!files.length) return

  // 파일 크기 및 형식 검사
  for (let i = 0; i < files.length; i++) {
    const file = files[i]

    // 파일 크기 제한 (10MB)
    if (file.size > 10 * 1024 * 1024) {
      alert('파일 크기는 10MB를 초과할 수 없습니다.')
      continue
    }

    // 이미지 파일만 허용
    if (!file.type.startsWith('image/')) {
      alert('이미지 파일만 업로드 가능합니다.')
      continue
    }

    // 이미지 미리보기 생성
    const reader = new FileReader()
    reader.onload = (e) => {
      uploadedImages.value.push({
        file: file,
        preview: e.target.result,
      })
    }
    reader.readAsDataURL(file)
  }

  // 파일 입력 초기화 (같은 파일 다시 선택 가능하도록)
  event.target.value = ''
}

// 이미지 제거
const removeImage = (index) => {
  uploadedImages.value.splice(index, 1)
}

// 게시글 제출
const submitPost = async () => {
  try {
    // 폼 유효성 검사
    if (!postData.value.title.trim()) {
      alert('제목을 입력해주세요.')
      return
    }

    if (!postData.value.content.trim()) {
      alert('내용을 입력해주세요.')
      return
    }

    if (!postData.value.category) {
      alert('카테고리를 선택해주세요.')
      return
    }

    // 이미지 업로드 처리 (실제 구현에서는 API 호출)
    const imageUrls = []
    if (uploadedImages.value.length > 0) {
      // 실제 구현에서는 이미지 업로드 API 호출
      // const formData = new FormData()
      // uploadedImages.value.forEach(img => {
      //   formData.append('images', img.file)
      // })
      // const response = await api.post('/upload', formData)
      // imageUrls = response.data.urls

      // 더미 URL로 대체
      imageUrls.push(...uploadedImages.value.map((img) => img.preview))
    }

    // 게시글 데이터 준비
    const postPayload = {
      ...postData.value,
      images: imageUrls,
    }

    // 게시글 등록 (실제 구현에서는 API 호출)
    // await api.post('/posts', postPayload)

    console.log('게시글 등록:', postPayload)

    // 성공 시 게시판으로 이동
    alert('게시글이 등록되었습니다.')
    router.push({ name: 'board' })
  } catch (error) {
    console.error('게시글 등록 중 오류가 발생했습니다:', error)
    alert('게시글 등록에 실패했습니다. 다시 시도해주세요.')
  }
}
</script>

<style scoped>
/* 추가 스타일이 필요한 경우 여기에 작성 */
</style>
