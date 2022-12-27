import { useCallback, useState } from 'react';
import { useParams } from 'react-router-dom';
import type { CmtData } from 'SingleComments/types';
import useGetSingleComments from './useGetSingleComments';
import useIntersection from 'AlarmList/hooks/useIntersection';

const useCommentFetch = () => {
  const [page, setPage] = useState(1);
  const [comment, setComments] = useState<CmtData[]>([]);
  const [isData, setIsData] = useState(true);

  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  const artworkId = parseInt(params.artworkId!);

  const { data, refetch } = useGetSingleComments(galleryId, artworkId, page);

  const callbackCmt = useCallback(
    (entries: IntersectionObserverEntry[], observer: IntersectionObserver) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          observer.unobserve(entry.target);
          refetch().then((res) => {
            setTimeout(() => {
              if (res.data) {
                if (res.data?.data.commentList.length >= 10) {
                  setIsData(true);
                  setPage(page + 1);
                  observer.observe(entry.target);
                } else {
                  setIsData(false);
                }
                setComments([...[...comment, ...res.data?.data.commentList]]);
              } else {
                setIsData(false);
              }
            }, 700);
          });
        }
      });
    },
    [comment],
  );

  const target = useIntersection(callbackCmt, { threshold: 0.3 });

  return { isData, comment, target, data, setIsData };
};

export default useCommentFetch;
