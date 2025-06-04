export const loadKakaoMap = () => {
  return new Promise((resolve, reject) => {
    const key = import.meta.env.VITE_KAKAO_API_KEY

    if (!key) {
      console.error('❌ Kakao API key가 비어있습니다.')
      return reject('API Key is missing')
    }

    if (window.kakao && window.kakao.maps) {
      return resolve(window.kakao)
    }

    const script = document.createElement('script')
    script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=${key}&autoload=false&libraries=services`
    script.onload = () => {
      window.kakao.maps.load(() => resolve(window.kakao))
    }
    script.onerror = () => {
      reject('Kakao SDK load failed')
    }

    document.head.appendChild(script)
  })
}
