<template>
  <nav>
    <HeaderNavBar />
  </nav>
  <main>
    <MainComponent />
  </main>
  <ChatBot class="fixed" style="bottom: 60px; right: 40px" />
  <footer class="fixed bottom-0 left-0 w-full bg-gray-800 text-white p-4 text-center">
    © 2025 WIT All rights reserved.
  </footer>
</template>
<script setup>
import MainComponent from '@/components/MainComponent.vue'
import HeaderNavBar from '@/components/HeaderNavBar.vue'
import ChatBot from '@/components/ChatBot.vue'
</script>

<style scoped></style>
