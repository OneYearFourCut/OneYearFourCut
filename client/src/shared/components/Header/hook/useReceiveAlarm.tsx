import { AlarmStore } from 'store/store';
import { useNavigate } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import apis from '../api';

const useReceiveAlarm = (isLoggedin: boolean) => {
  const { alarmIsOpen } = AlarmStore();
  const navigate = useNavigate();
  const { data, status, refetch, isStale } = useQuery(
    ['useReceiveAlarm'],
    apis.getCheckAlarm,
    {
      // enabled: false, //배포시 삭제
      refetchInterval: () => (isLoggedin && !alarmIsOpen ? 3000 : false),
      retry: true,
      retryDelay: 1000,
      refetchOnWindowFocus: false,
      refetchOnMount: false,
      refetchIntervalInBackground: false,
      onError(err) {
        console.log(err);
      },
    },
  );

  const onClick = () => {
    navigate('/alarmList');
  };

  return { alarmIsOpen, onClick, data, status };
};

export default useReceiveAlarm;
