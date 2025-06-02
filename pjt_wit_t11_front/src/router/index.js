// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user' // 사용자 스토어 가져오기

import MainPageView from '@/views/MainPageView.vue'
import LocationSelection from '@/components/LocationSelection.vue'
import LoginView from '@/views/LoginView.vue'
import SignUpView from '@/views/SignUpView.vue'
import BoardView from '@/views/BoardView.vue'
import PostCreateView from '@/components/PostCreateView.vue'
import PostDetailView from '@/components/PostDetailView.vue'
import MyPageView from '@/views/MyPageView.vue'
import TripDetailView from '@/views/TripDetailView.vue'
import PasswordResetView from '@/components/password/PasswordResetView.vue'
import PasswordResetRequestView from '@/components/password/PasswordResetRequestView.vue'

const routes = [
  { path: '/', name: 'home', component: MainPageView },
  { path: '/LS', name: 'LS', component: LocationSelection, meta: { requiresAuth: true } },
  { path: '/login', name: 'login', component: LoginView },
  { path: '/signup', name: 'signup', component: SignUpView },
  { path: '/Board', name: 'Board', component: BoardView },
  { path: '/PostCreate', name: 'PostCreate', component: PostCreateView },
  { path: '/PostDetail', name: 'PostDetail', component: PostDetailView },
  { path: '/mypage', name: 'mypage', component: MyPageView, meta: { requiresAuth: true } },
  { path: '/logout', name: 'logout', component: MyPageView },
  { path: '/schedule/:scheduleId', name: 'scheduleDetail', component: TripDetailView },
  { path: '/schedule/:scheduleId/edit', name: 'editSchedule', component: LocationSelection },
  {
    path: '/reset-password',
    name: 'passwordResetView',
    component: PasswordResetView,
  },
  {
    path: '/password/PasswordResetRequestView',
    name: 'passwordResetRequestView',
    component: PasswordResetRequestView,
  },
]

const router = createRouter({
  history: createWebHistory(), // HTML5 히스토리 모드
  routes,
  scrollBehavior(to, from, savedPosition) {
    // 페이지 전환 시 스크롤 위치 제어 (선택)
    if (savedPosition) return savedPosition
    return { top: 0 }
  },
})

// router/index.js
router.beforeEach((to, from, next) => {
  const authStore = useUserStore()

  if (to.meta.requiresAuth && !authStore.token) {
    // 로그인 필요 페이지에 접근하려는 경우
    next({ name: 'login' })
  } else {
    // 로그인한 경우 다음 페이지로 이동
    next()
  }
})

export default router
