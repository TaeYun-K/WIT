import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import tailwindcss from '@tailwindcss/vite'

// https://vite.dev/config/
export default defineConfig({
  base: './',
  plugins: [vue(), vueDevTools(), tailwindcss()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  server: {
    proxy: {
      // /api 로 들어오는 요청은 모두 8080로 포워딩
      '/api': {
        target: 'https://port-0-wit-mbhk6pks03b248b5.sel4.cloudtype.app',
        changeOrigin: true,

        rewrite: (path) => path.replace(/^\/api/, '/api'),
      },
    },
  },
})
