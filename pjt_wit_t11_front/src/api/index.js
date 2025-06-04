import axios from 'axios'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL, // 배포와 개발 환경 모두 대응 가능
  withCredentials: true,
})

export default api
