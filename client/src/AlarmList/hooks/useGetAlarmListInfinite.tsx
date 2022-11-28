import { useQuery } from '@tanstack/react-query';
import api from '../api';
import { getParams } from '../types';

const useGetAlarmListInfinite = (params:getParams) => {
  const { refetch } = useQuery(
    ['useGetAlarmListInfinite',params],
    () =>
      api.getAlarmList(params),
    {
      enabled: false,
      onError(err) {
        console.log(err);
      },
    },
  );
   //선언형이라 명령형과 다르게 매개변수를 전달불가
  return { refetch };
};

export default useGetAlarmListInfinite;
