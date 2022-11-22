import * as S from './Delete.style';
import { Btn } from 'shared/components/Buttons';

const DeleteUser = () => {
  return (
    <S.Body>
      <S.Title>
        올해 네 컷 서비스에서 <br />
        탈퇴하시겠습니까?
      </S.Title>
      <S.Script>
        회원 탈퇴 시,
        <br />
        기존에 만들어둔 전시관에 있는 사진과 덧글이
        <br />
        모두 삭제되며 복구가 불가능합니다.
      </S.Script>
      <S.Script>
        탈퇴는 즉시 이루어지며 <br />
        회원 정보가 영구적으로 삭제됩니다.
      </S.Script>
      <S.Button>
        <S.Cancle>취소</S.Cancle>
        <Btn className='square red'>탈퇴하기</Btn>
      </S.Button>
    </S.Body>
  );
};

export default DeleteUser;
