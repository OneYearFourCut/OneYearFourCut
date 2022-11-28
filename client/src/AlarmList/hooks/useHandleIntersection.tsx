import { useCallback, useState } from 'react';
import useGetAlarmListInfinite from './useGetAlarmListInfinite';
import useIntersection from './useIntersection';
import { ALData, ALDataType } from 'AlarmList/types';

//배포시 삭제
let tempdata: ALData[] = [
  {
    type: 'LIKE_ARTWORK',
    userNickname: 'kdy',
    read: false,
    artworkId: 1,
    createdAt: '2012-11-21 오전 10: 14',
    artworkTitle: '작품제목입니다.1',
  },{
    type: 'COMMENT_ARTWORK',
    userNickname: 'kdy',
    read: false,
    artworkId: 1,
    createdAt: '2012-11-21 오전 10: 14',
    artworkTitle: '작품제목입니다.2',
  },{
    type: 'COMMENT_GALLERY',
    userNickname: 'kdy',
    read: false,
    artworkId: 1,
    createdAt: '2012-11-21 오전 10: 14',
    artworkTitle: '작품제목입니다.3',
  },{
    type: 'POST_ARTWORK',
    userNickname: 'kdy',
    read: false,
    artworkId: 1,
    createdAt: '2012-11-21 오전 10: 14',
    artworkTitle: '작품제목입니다.4',
  },
];

const useHandleIntersection = () => {
  const [isData, setIsData] = useState(true);
  const [alarmListData, setAlarmListData] = useState<ALData[]>(tempdata);
  const [filter, setFilter] = useState<string>('All');
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
                  setPage(page + 1);
                  observer.observe(entry.target);
                } else {
                  setIsData(false);
                }
                setAlarmListData([...[...alarmListData, ...res.data.data]]);
              }
              //데이터가 없을때
              else {
                setIsData(false);
              }
            }, 500);
          });
        }
      });
    },
    [alarmListData],
  );

  const target = useIntersection(callbackfunc, { threshold: 0.5 });

  return { isData, alarmListData, target, filter, setFilter, setPage, setAlarmListData, setIsData };
};

export default useHandleIntersection;
