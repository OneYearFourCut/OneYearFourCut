import 'moment/locale/ko';
import moment from 'moment';
import * as S from './style';
import Message from 'assets/Icon/message';

interface ChatListProps {
  readonly chatRoomId?: number;
  readonly galleryId?: number;
  readonly profile?: string;
  readonly nickName?: string;
  readonly chattedAt?: string;
  readonly lastChatMessage?: string;
}
export default function Index({
  chatRoomId,
  galleryId,
  profile,
  nickName,
  chattedAt,
  lastChatMessage,
}: ChatListProps) {
  let nowTime = moment(chattedAt).fromNow();
  console.log(nowTime);
  return (
    <S.chatContainer>
      <S.ProfileBox>
        <S.ProfileCircle>
          <S.Profile src={profile} />
        </S.ProfileCircle>
      </S.ProfileBox>
      <S.InfoBox>
        <S.NameBox>
          <h2>{nickName}</h2>
          <div>{nowTime}</div>
        </S.NameBox>
        <div>{lastChatMessage}</div>
      </S.InfoBox>
      <S.ChatBox>
        <Message />
      </S.ChatBox>
    </S.chatContainer>
  );
}
