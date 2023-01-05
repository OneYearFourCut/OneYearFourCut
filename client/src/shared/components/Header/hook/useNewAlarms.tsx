import apis from '../api';
import { useEffect, useState, useRef } from 'react';
import { NativeEventSource, EventSourcePolyfill } from 'event-source-polyfill';
import { AlarmStore } from 'store/store';
import { useNavigate } from 'react-router-dom';
import { getStoredToken } from 'Intro/hooks/tokenStorage';

export const useNewAlarms = (isLoggedin: boolean) => {
  const [newAlarms, setNewAlarms] = useState(false);
  const { alarmIsOpen } = AlarmStore();
  const navigate = useNavigate();
  const eventSource = useRef<any>(null);
  const reConnectCount = useRef<number>(0);

  const eventSourceConnect = () => {
    const EventSource = EventSourcePolyfill || NativeEventSource;
    eventSource.current = new EventSource(
      `${process.env.REACT_APP_SERVER_URL}/members/me/alarms/connect`,
      {
        headers: {
          Authorization: getStoredToken()?.access_token!,
        },
        heartbeatTimeout: 60*60*1000,
      },
    );
    eventSourceAddEvent();
  };

  const eventSourceAddEvent = () => {
    eventSource.current.addEventListener('newAlarms', (e: any) => {
      setNewAlarms(JSON.parse(e.data));
      reConnectCount.current = 0;
    });
    eventSource.current.addEventListener('error', (e: any) => {
      if (e.status === 456) {
        apis
          .getRefreshedToken()
          .then((res) => {
            eventSourceClose();
            eventSourceConnect();
          })
          .catch((err) => console.log(err));
      } else if (e.status === 457) {
        alert('로그인이 만료되었습니다.');
        window.location.replace('/');
      } else {
        if (reConnectCount.current < 3) {
          reConnectCount.current++;
          eventSourceClose();
          eventSourceConnect();
        } else {
          alert('eventSource server error');
          window.location.replace('/');
        }
      }
    });

    eventSource.current.addEventListener('close', eventSourceClose);
  };

  const eventSourceClose = () => {
    console.log('eventSource Close');
    eventSource.current.close();
  };
  useEffect(() => {
    if (isLoggedin && alarmIsOpen === false) {
      eventSourceConnect();
      return () => eventSourceClose();
    }
  }, [isLoggedin, alarmIsOpen]);

  const onClick = () => {
    navigate('/alarmList');
  };

  return { newAlarms, alarmIsOpen, onClick };
};
