// src/stores/user.js
import { defineStore } from 'pinia'
<<<<<<< HEAD
=======
import axios from 'axios'
>>>>>>> 8dabe5cd9dfd717d666ec535e96f37c38fa3d88d
import api from '@/api'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('jwt') || '', // JWT 토큰
    user: {
      userId: '',
      username: '',
      email: '',
      schedules: [], // 스케줄 목록
    },
  }),
  actions: {
    async fetchUserInfo() {
      const token = localStorage.getItem('jwt')
      if (!token) return

      const response = await api.get('/api/user/mypage', {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      this.user = response.data
    },

    async deleteSchedule(scheduleId) {
      const token = localStorage.getItem('jwt')
      if (!token) return

      await api.delete(`/api/user/schedule/${scheduleId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })

      await this.fetchUserInfo() // 스케줄 삭제 후 사용자 정보 다시 가져오기
    },

    logout() {
      this.token = ''
      this.user = {
        userId: '',
        username: '',
        email: '',
        schedules: [],
      }
      localStorage.removeItem('jwt') // 로컬 스토리지에서 JWT 토큰 제거
    },
  },
})
