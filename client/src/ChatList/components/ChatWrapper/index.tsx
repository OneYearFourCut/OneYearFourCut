import * as S from './style';
import Message from 'assets/Icon/message';

interface ChatListProps {
  chatRoomId: number;
  galleryId: number;
  profile: string;
  nickName: string;
  chattedAt: string;
  lastChatMessage: string;
}
export default function index({
  chatRoomId,
  galleryId,
  profile,
  nickName,
  chattedAt,
  lastChatMessage,
}: ChatListProps) {
  return (
    <S.chatContainer>
      <S.ProfileBox>
        <S.ProfileCircle>
          <S.Profile src='/images/1.jpg' />
        </S.ProfileCircle>
      </S.ProfileBox>
      <S.InfoBox>
        <S.NameBox>
          <h2>이름</h2>
          <div>n분 전</div>
        </S.NameBox>
        <div>마지막 메세지 내용</div>
      </S.InfoBox>
      <S.ChatBox>
        <Message />
      </S.ChatBox>
    </S.chatContainer>
  );
}
