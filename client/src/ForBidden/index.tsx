import * as S from './ForBidden.style';
import FourZeroFour from 'shared/components/Icons/ForbiddenIcon';
import { Btn } from '../shared/components/Buttons/index';

const ForBidden = () => {
  return (
    <S.Body>
      <FourZeroFour></FourZeroFour>
      <S.Title>페이지를 찾지 못하였습니다</S.Title>
      <S.Script>
        <div>찾을 수 없는 페이지입니다!</div>
        <div> 페이지가 삭제되었거나, 잘못된 경로로 이동하셨습니다!</div>
      </S.Script>
      <Btn>메인 페이지로 돌아가기</Btn>
    </S.Body>
  );
};

export default ForBidden;
