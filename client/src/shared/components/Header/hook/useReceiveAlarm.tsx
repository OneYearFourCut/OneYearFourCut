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
      enabled: isLoggedin,
      refetchInterval: () => (isLoggedin && !alarmIsOpen ? 4000 : false),
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
