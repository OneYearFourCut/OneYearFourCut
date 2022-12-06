import * as S from './Delete.style';
import { Btn } from 'shared/components/Buttons';

const DeleteGallery = () => {
  return (
    <S.Body>
      <S.Title>
        승필의 1년 돌아보기
        <br /> 전시회를 삭제하시겠습니까?
      </S.Title>
      <S.Script>
        전시회를 삭제하게 되는 경우,
        <br />
        복원이 불가능하며 현재까지 전시관에 업로드된
        <br /> 사진과 덧글이 모두 삭제됩니다.
      </S.Script>
      <S.Script>
        회원 탈퇴는 이루어지지 않으며 <br />
        회원 탈퇴시, 전시회 삭제가 자동으로 이루어집니다
      </S.Script>
      <S.Button>
        <S.Cancle>취소</S.Cancle>
        <Btn className='square red'>삭제하기</Btn>
      </S.Button>
    </S.Body>
  );
};

export default DeleteGallery;
