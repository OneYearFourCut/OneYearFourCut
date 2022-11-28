import * as S from './style';
import { IoChatbubbleSharp } from 'react-icons/io5';
import { rem } from 'polished';

const index = () => {
  
  return (
    <>
      <S.Btn href={process.env.REACT_APP_KAKAO_AUTH_URL}>
        <IoChatbubbleSharp size={rem(20)} />
        <p className='label'>카카오 로그인</p>
      </S.Btn>
    </>
  );
};

export default index;
