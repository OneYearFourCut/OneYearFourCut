import { IRoomInfo } from '../types'
import { ChatRoomHeaderContainer, ChatRoomProfileImg } from './style';
export const ChatRoomHeader = ({
  chatRoomMemberInfoList,
}: {
  chatRoomMemberInfoList: IRoomInfo[];
}) => {

  return (
    <ChatRoomHeaderContainer>
      <ChatRoomProfileImg src={chatRoomMemberInfoList[0].profile} alt='' />
      <h4>{chatRoomMemberInfoList[0].nickname}님과의 대화</h4>
    </ChatRoomHeaderContainer>
  );
};
