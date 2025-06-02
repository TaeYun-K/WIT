// utils/routeApi.js
import axios from 'axios'

// 환경 변수
const KAKAO_KEY = import.meta.env.VITE_KAKAO_API_KEY
const GOOGLE_KEY = import.meta.env.VITE_GOOGLE_API_KEY

/**
 * 카카오 다중(여러 경유지) 길찾기
 * POST https://apis-navi.kakaomobility.com/v1/waypoints/directions
 * @param {Array<{from:{lat,lng}, to:{lat,lng}}>} segments
 * @returns {Promise<number[]>}
 */
export async function kakaoMultiRoute(segments) {
  const url = 'https://apis-navi.kakaomobility.com/v1/waypoints/directions'
  // 첫 번째 origin, 마지막 destination, 중간 to 들은 waypoints 로
  const origin = segments[0].from
  const destination = segments[segments.length - 1].to
  const waypoints = segments.slice(0, -1).map((s) => ({
    x: s.to.longitude,
    y: s.to.latitude,
  }))

  const body = {
    origin: { x: origin.longitude.toString(), y: origin.latitude.toString() },
    destination: { x: destination.longitude.toString(), y: destination.latitude.toString() },
    waypoints,
    priority: 'RECOMMEND',
  }
  try {
    const res = await axios.post(url, body, {
      headers: {
        Authorization: `KakaoAK ${KAKAO_KEY}`,
        'Content-Type': 'application/json',
      },
    })
    // 안전하게 sections 검사
    const route = res.data.routes?.[0]
    const parts = Array.isArray(route?.sections)
      ? route.sections
      : Array.isArray(route?.legs)
        ? route.legs
        : []
    return parts.map((part) => Math.round(part.duration / 60))
  } catch (e) {
    console.error('kakaoMultiRoute error:', e)
    // 오류 시에도 빈 배열로 처리
    return []
  }
}

/**
 * 카카오 단일(한 구간) 길찾기
 * GET https://apis-navi.kakaomobility.com/v1/directions
 * @param {{lat,lng}} from
 * @param {{lat,lng}} to
 * @returns {Promise<number>}
 */
export async function kakaoSingleRoute(from, to) {
  const url = 'https://apis-navi.kakaomobility.com/v1/directions'
  try {
    const res = await axios.get(url, {
      params: {
        origin: `${from.longitude},${from.latitude}`,
        destination: `${to.longitude},${to.latitude}`,
        priority: 'RECOMMEND',
      },
      headers: {
        Authorization: `KakaoAK ${KAKAO_KEY}`,
      },
    })
    // 1) routes 배열 검사
    const route = res.data.routes?.[0]
    if (!route) {
      console.warn('kakaoSingleRoute: no route in response', res.data)
      return 0
    }
    // 에러 코드가 있으면(101,102 등) 바로 0 리턴
    if (typeof route.result_code === 'number' && route.result_code !== 0) {
      console.warn(`kakaoSingleRoute: error code ${route.result_code} – ${route.result_msg || ''}`)
      return 0
    }
    // 2) sections 배열로 duration 추출
    const sections = Array.isArray(route.sections) ? route.sections : []
    if (sections.length === 0) {
      console.warn('kakaoSingleRoute: no sections in response', res.data)
      return 0
    }
    return Math.round(sections[0].duration / 60)
  } catch (e) {
    console.error('kakaoSingleRoute error:', e)
    return 0
  }
}

/**
 * 구글 대중교통 길찾기 (Transit)
 * @param {{lat,lng}} from
 * @param {{lat,lng}} to
 * @returns {Promise<number>} 소요시간(분)
 */
export function googleTransitRoute(from, to) {
  return new Promise((resolve, reject) => {
    if (!window.google || !window.google.maps) {
      return reject(new Error('Google Maps JS API가 로드되지 않았습니다.'))
    }
    const directionsService = new google.maps.DirectionsService()
    directionsService.route(
      {
        origin: new google.maps.LatLng(from.latitude, from.longitude),
        destination: new google.maps.LatLng(to.latitude, to.longitude),
        travelMode: google.maps.TravelMode.TRANSIT,
      },
      (result, status) => {
        if (status === 'OK' && result.routes.length) {
          const leg = result.routes[0].legs[0]
          // leg.duration.value 는 초 단위
          const minutes = Math.round((leg.duration.value || 0) / 60)
          resolve(minutes)
        } else {
          reject(new Error('DirectionsService 요청 실패: ' + status))
        }
      },
    )
  })
}
