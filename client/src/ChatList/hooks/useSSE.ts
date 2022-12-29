import { useEffect } from 'react';
import { setStoredToken, getStoredToken } from 'Intro/hooks/tokenStorage';
import { NativeEventSource, EventSourcePolyfill } from 'event-source-polyfill';

// let ACCESS_TOKEN = getStoredToken()?.access_token;
// // API 주소 주시면 연결
// // 너와 나의 연결 고리 우리 안의 소리
// const EventSource = EventSourcePolyfill || NativeEventSource;

// const eventSource = new EventSource(
//   `${process.env.REACT_APP_SERVER_URL}/subscribe`,
//   {
//     headers: {
//       Authorization: ACCESS_TOKEN!,
//     },
//   },
// );
