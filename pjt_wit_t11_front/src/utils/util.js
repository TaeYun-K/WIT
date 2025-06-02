// src/utils/imageUtil.js

// default 이미지 경로를 반환하는 함수
export function getDefaultImage(typeId) {
  switch (typeId) {
    case 2:
      return 'src/assets/defaults/restaurant.png' // 음식점
    case 12:
    case 39:
      return 'src/assets/defaults/attraction.png' // 관광지
    case 32:
      return 'src/assets/defaults/accommodation.png' // 숙소
    case 1:
      return 'src/assets/defaults/cafe.png' // 카페 (예시)
    default:
      return 'src/assets/logo.png' // 기본 이미지
  }
}
