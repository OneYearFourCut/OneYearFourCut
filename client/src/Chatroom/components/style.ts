import styled from 'styled-components';
import { rem } from 'polished';

const DefualtContainer = styled.div`
  width: ${rem(428)};
  height: 95vh;
  display: flex;
  flex-direction: column;
  background-color: ${({ theme }) => theme.colors.black_007};
`;

const ChatRoomHeaderContainer = styled.div`
  width: ${rem(428)};
  height: 7vh;
  display: flex;
  align-items: center;
`;

const ChatRoomContentsContainer = styled.div`
  width: ${rem(428)};
  height: 76vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow-y: auto;
  ::-webkit-scrollbar {
    width: ${rem(6)};
  }

  ::-webkit-scrollbar-thumb {
    background: ${({ theme }) => theme.colors.green_008};
    border-radius: ${rem(10)};
  }
`;

const ChatRoomInputContainer = styled.div`
  width: ${rem(428)};
  height: 12vh;
  textarea {
    width: ${rem(428)};
    height: 7vh;
    padding: ${rem(10)};
    resize: none;
    outline: none;
    border: none;
    background-color: ${({ theme }) => theme.colors.green_008};
  }
  & textarea::-webkit-scrollbar {
    width: ${rem(6)};
  }

  & textarea::-webkit-scrollbar-thumb {
    background: ${({ theme }) => theme.colors.green_008};
    border-radius: ${rem(10)};
  }

  button {
    width: ${rem(60)};
    height: 3vh;
    margin: ${rem(5)} ${rem(5)} ${rem(5)} ${rem(360)};
    border: none;
    border-radius: ${rem(7)};
    background-color: ${({ theme }) => theme.colors.green_008};
  }
`;

//채팅 컴포넌트
const ChatContainer = styled.div`
  width: ${rem(428)};
  height: auto;
  display: flex;
  padding: ${rem(5)};
  border: solid 1px red;
`;

//채팅 내용 리스트 박스
const ChatContentListBox = styled.div`
  width: ${rem(280)};
  height: auto;
  display: flex;
  flex-direction: column;
`;

//실제 채팅 내용
const ChatContentBox = styled.div`
  margin: ${rem(8)} 0 ${rem(8)} 0;

  div {
    display: inline-block;
    height: auto;
    padding: ${rem(5)};
    white-space: pre-wrap;
    border-radius: ${rem(5)};
    font-size: ${rem(12)};
    background-color: ${({ theme }) => theme.colors.green_008};
  }
`;

const ChatRoomProfileImg = styled.img`
  width: ${rem(46)};
  height: ${rem(46)};
  border-radius: ${rem(10)};
  margin: ${rem(10)};
`;

const DayDateBox = styled.div`
  width: ${rem(200)};
  height: ${rem(25)};
  border-radius: ${rem(15)};
  text-align: center;
  line-height: ${rem(25)};
  font-size: ${rem(13)};
  background-color: ${({ theme }) => theme.colors.black_008};
  margin: ${rem(10)};
`;

export {
  DefualtContainer,
  ChatRoomHeaderContainer,
  ChatRoomContentsContainer,
  ChatRoomInputContainer,
  ChatRoomProfileImg,
  DayDateBox,
  ChatContainer,
  ChatContentListBox,
  ChatContentBox,
};
