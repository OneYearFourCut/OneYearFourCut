import apis from '../api';
import { useEffect, useState } from 'react';
import { NativeEventSource, EventSourcePolyfill } from 'event-source-polyfill';
import { AlarmStore } from 'store/store';
import { useNavigate } from 'react-router-dom';
import { getStoredToken } from 'Intro/hooks/tokenStorage';

export const useNewAlarms = (isLoggedin: boolean) => {
  const [newAlarms, setNewAlarms] = useState(false);
  const { alarmIsOpen } = AlarmStore();
  const navigate = useNavigate();

  useEffect(() => {
    if (isLoggedin) {
      const EventSource = EventSourcePolyfill || NativeEventSource;
      const eventSource = new EventSource(
        `${process.env.REACT_APP_SERVER_URL}/members/me/alarms/read`,
        {
          headers: {
            Authorization: getStoredToken()?.access_token!,
          },
        },
      );

      eventSource.addEventListener('newAlarms', (e: any) => {
        setNewAlarms(e.data);
      });

      eventSource.addEventListener('error', (e: any) => {
        if (e.status === 456) {
          //재발급 로직 -> 테스트 해봐야함.
          apis
            .getCheckAlarm()
            .then((res) => setNewAlarms(res.data.readAlarmExist));
        }
      });

      eventSource.addEventListener('close', () => eventSource.close());
      return () => eventSource.close();
    }
  }, [isLoggedin]);

  const onClick = () => {
    navigate('/alarmList');
  };

  return { newAlarms, alarmIsOpen, onClick };
};
