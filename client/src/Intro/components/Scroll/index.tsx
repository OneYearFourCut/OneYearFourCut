import * as S from './style';
import Envelope from '../Envelope';
import Kakao from '../KakaoBtn';
import Letter from '../Letter';
import { useEffect, useRef, useState } from 'react';
import AIntro from '../AIntro';
import BIntro from '../BIntro';
import CIntro from '../CIntro';

const Index = () => {
  const homeRef = useRef<HTMLInputElement>(null);
  const goRef = useRef<HTMLInputElement>(null);
  const nextRef = useRef<HTMLInputElement>(null);
  const lastRef = useRef<HTMLInputElement>(null);

  const onHomeClick = () => {
    // 그 ref가 있는 곳으로 부드럽게 이동함.
    homeRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  const onGoClick = () => {
    // 그 ref가 있는 곳으로 부드럽게 이동함.
    goRef.current?.scrollIntoView({ behavior: 'smooth' });
  };
  const onNextClick = () => {
    // 그 ref가 있는 곳으로 부드럽게 이동함.
    nextRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  const onLastClick = () => {
    // 그 ref가 있는 곳으로 부드럽게 이동함.
    lastRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  const [isOpen, setIsOpen] = useState<boolean>(false);

  const onClick = () => {
    setIsOpen(!isOpen);
  };

  return (
    <S.Container>
      <Kakao />
      <div>
        <S.EnvelopeWrapper ref={homeRef}>
          <S.Envelope className={isOpen ? 'open' : 'close'}>
            <S.Front onClick={onClick} className='flap'></S.Front>
            <S.Front onClick={onClick} className='pocket'></S.Front>
            <Letter handleNext={onGoClick} isOpen={isOpen} />
          </S.Envelope>
        </S.EnvelopeWrapper>
        <S.Box ref={goRef}>
          <AIntro />
          <S.OpenBtn onClick={onNextClick}>Down</S.OpenBtn>
        </S.Box>
        <S.Box ref={nextRef}>
          <BIntro />
          <S.OpenBtn className='top' onClick={onHomeClick}>
            Top
          </S.OpenBtn>
        </S.Box>
      </div>
    </S.Container>
  );
};

export default Index;
