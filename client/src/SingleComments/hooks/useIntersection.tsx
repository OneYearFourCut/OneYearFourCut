import { useRef, useEffect, useCallback } from 'react';

const useIntersection = (
  callback: IntersectionObserverCallback,
  options?: IntersectionObserverInit,
) => {
  const target = useRef(null);

  useEffect(() => {
    const observer = new IntersectionObserver(callback, options);
    target.current && observer.observe(target.current);

    return () => observer.disconnect(); //인스턴스가 관찰하는 모든요소의 관찰을 중지
  }, [target, callback]);
  //한번실행되고 말면 usecallback이라 클로저에의해 목데이터값이 안바뀜
  return target;
};

export default useIntersection;
