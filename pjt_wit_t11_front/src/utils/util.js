import attractionImg from '@/assets/defaults/attraction.png'
import restaurantImg from '@/assets/defaults/restaurant.png'
import accommodationImg from '@/assets/defaults/accommodation.png'
import cafeImg from '@/assets/defaults/cafe.png'
import logoImg from '@/assets/logo.png'

// default 이미지 경로를 반환하는 함수
export function getDefaultImage(typeId) {
  switch (typeId) {
    case 2:
      return restaurantImg
    case 12:
    case 39:
      return attractionImg
    case 32:
      return accommodationImg
    case 1:
      return cafeImg
    default:
      return logoImg
  }
}
