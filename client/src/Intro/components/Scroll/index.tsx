import * as S from './style';
import Envelope from '../Envelope';
import Kakao from '../Kakao';
import { useEffect, useRef } from 'react';

const Index = () => {
  const homeRef = useRef<HTMLInputElement>(null);
  const goRef = useRef<HTMLInputElement>(null);
  const onHomeClick = () => {
    // 그 ref가 있는 곳으로 부드럽게 이동함.
    homeRef.current?.scrollIntoView({ behavior: 'smooth' });
  };
  const onGoClick = () => {
    // 그 ref가 있는 곳으로 부드럽게 이동함.
    goRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  return (
    <S.Container>
      <Envelope refs={homeRef} />

      <S.Box></S.Box>
      <S.Box className='yellow'>
        <div ref={goRef}>
          <h1>
            자 여기에 사이트 소개 페이지를 시작해봅시다 여러분의 올 한 해는
            어떠셨나요?
          </h1>
        </div>
      </S.Box>
      <S.Button onClick={onHomeClick}>
        누르면 이동한다고? 진짜 가네 오키
      </S.Button>
      <Kakao />
    </S.Container>
  );
};

export default Index;
