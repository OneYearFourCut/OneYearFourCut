import { useCallback, useState } from 'react';
import type { ALData } from 'AlarmList/types';
import useGetAlarmListInfinite from './useGetAlarmListInfinite';
import useIntersection from './useIntersection';

const useHandleIntersection = () => {
  const [isData, setIsData] = useState(true);
  const [alarmListData, setAlarmListData] = useState<ALData[]>([]);
  const [filter, setFilter] = useState<string>('ALL');
  const [page, setPage] = useState<number>(1);

  const { refetch } = useGetAlarmListInfinite({ filter, page });

  const callbackfunc = useCallback(
    (entries: IntersectionObserverEntry[], observer: IntersectionObserver) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          observer.unobserve(entry.target);
          refetch().then((res) => {
            setTimeout(() => {
              //데이터가 있을떄
              if (res.data && res.data.data) {
                if (res.data.data.length >= 7) {
                  //7개 다왔을때
                  setIsData(true);
                  setPage((page) => page + 1);
                  observer.observe(entry.target);
                } else {
                  setIsData(false);
                }
                setAlarmListData((Data) => [...Data, ...res.data.data]);
              }
              //데이터가 없을때
              else {
                setIsData(false);
              }
            }, 700);
          });
        }
      });
    },
    [filter],
  );

  const target = useIntersection(callbackfunc, { threshold: 0.5 });

  return {
    isData,
    alarmListData,
    target,
    filter,
    setFilter,
    setPage,
    setAlarmListData,
    setIsData,
  };
};

export default useHandleIntersection;
