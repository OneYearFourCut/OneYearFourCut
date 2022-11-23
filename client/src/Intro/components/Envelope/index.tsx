import { useState, forwardRef } from 'react';
import Letter from '../Letter';
import * as S from './style';

type content = {
  refs: any;
};
function Index(props: { refs: any }): JSX.Element {
  const [isOpen, setIsOpen] = useState<boolean>(false);

  const onClick = () => {
    setIsOpen(!isOpen);
  };

  return (
    <>
      <S.EnvelopeWrapper>
        <S.Envelope className={isOpen ? 'open' : 'close'}>
          <S.Front onClick={onClick} className='flap'></S.Front>
          <S.Front onClick={onClick} className='pocket'></S.Front>
          <Letter isOpen={isOpen} />
        </S.Envelope>
      </S.EnvelopeWrapper>
    </>
  );
}

export default Index;
