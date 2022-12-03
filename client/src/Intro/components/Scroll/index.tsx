import * as S from './style';
import Envelope from '../Envelope';
import Kakao from '../KakaoBtn';
import Letter from '../Letter';
import { useEffect, useRef, useState } from 'react';
import AIntro from '../AIntro';

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

  const [isOpen, setIsOpen] = useState<boolean>(false);

  const onClick = () => {
    setIsOpen(!isOpen);
  };

  return (
    <S.Container>
      <Kakao />
      <S.EnvelopeWrapper ref={homeRef}>
        <S.Envelope className={isOpen ? 'open' : 'close'}>
          <S.Front onClick={onClick} className='flap'></S.Front>
          <S.Front onClick={onClick} className='pocket'></S.Front>
          <Letter handleNext={onGoClick} isOpen={isOpen} />
        </S.Envelope>
      </S.EnvelopeWrapper>
      <S.Box ref={goRef}>
        <AIntro />
      </S.Box>
      <S.Button onClick={onHomeClick}>홈으로 이동</S.Button>
    </S.Container>
  );
};

export default Index;
