import { IRoomInfo } from 'ChatRoom/types';
import { ChatRoomHeaderContainer, ChatRoomProfileImg } from './style';
import { loginStore } from 'store/store';
import { useEffect, useState } from 'react';
export const ChatRoomHeader = ({
  chatRoomMemberInfoList,
}: {
  chatRoomMemberInfoList: IRoomInfo[];
}) => {
  const memberId = loginStore().user?.memberId;
  const [chatRoomImg, setChatRoomImg] = useState('');
  const [chatRoomTitle, setChatRoomTitle] = useState('');

  useEffect(() => {
    
  }, []);

  return (
    <ChatRoomHeaderContainer>
      <ChatRoomProfileImg src={chatRoomMemberInfoList[0].profile} alt='' />
      <h4>{chatRoomMemberInfoList[0].nickname}님과의 대화</h4>
    </ChatRoomHeaderContainer>
  );
};
