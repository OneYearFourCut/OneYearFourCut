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
  padding: ${rem(10)};
  margin-top: ${rem(10)};
`;

const ChatRoomBodyContainer = styled.div`
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
  display: flex;
  align-items: center;
  border-top: solid 1px ${({ theme }) => theme.colors.green_008};
  textarea {
    width: 85%;
    height: 7vh;
    padding: ${rem(10)};
    resize: none;
    outline: none;
    border: none;
  }
  & textarea::-webkit-scrollbar {
    width: ${rem(6)};
  }

  & textarea::-webkit-scrollbar-thumb {
    background: ${({ theme }) => theme.colors.green_008};
    border-radius: ${rem(10)};
  }

  button {
    width: 15%;
    height: 7vh;
    margin: ${rem(5)};
    border: none;
    border-radius: ${rem(7)};
    background-color: ${({ theme }) => theme.colors.green_008};
  }
`;

//채팅 컴포넌트
const ChatContainer = styled.div<{ type: string }>`
  width: 99%;
  height: auto;
  display: flex;
  justify-content: ${({ type }) => type};
  padding: 0 ${rem(15)} 0 ${rem(15)};
  margin: ${rem(5)} 0 ${rem(5)} 0;
`;

//채팅 내용 리스트 박스
const ChatContentListBox = styled.div`
  width: ${rem(340)};
  height: auto;
  display: flex;
  flex-direction: column;
  .useName {
    font-weight: 400;
    font-size: ${rem(13)};
    margin: ${rem(7)} 0 0 ${rem(7)};
  }
`;

//실제 채팅 내용
const ChatContentBox = styled.div<{ type: string}>`
  display: flex;
  align-items: end;
  justify-content: ${({ type }) => type};
  margin: ${rem(5)} 0 ${rem(5)} 0;
  .leftContent,
  .rightContent {
    display: inline-block;
    position: relative;
    max-width: 85%;
    height: auto;
    padding: ${rem(5)} ${rem(10)} ${rem(5)} ${rem(10)};
    margin: 0 ${rem(5)} 0 ${rem(5)};
    white-space: pre-wrap;
    border-radius: ${rem(5)};
    font-size: ${rem(12)};
    background-color: ${({ theme, type }) =>
      type === 'left' ? theme.colors.black_011 : 'transparent'};
    border: solid 2px
      ${({ theme, type }) =>
        type === 'left' ? theme.colors.black_011 : theme.colors.green_008};
  }

  .leftContent.first::before {
    content: '';
    position: absolute;
    top: ${rem(5)};
    left: ${rem(-10)};
    width: 0;
    height: 0;
    border: ${rem(5)} solid transparent;
    border-top: ${rem(5)} solid ${({ theme }) => theme.colors.black_011};
    border-right: ${rem(5)} solid ${({ theme }) => theme.colors.black_011};
    border-radius: ${rem(3)};
  }

  .rightContent.first::after {
    content: '';
    position: absolute;
    top: ${rem(5)};
    right: ${rem(-10)};
    width: 0;
    height: 0;
    border: ${rem(5)} solid transparent;
    border-top: ${rem(5)} solid ${({ theme }) => theme.colors.green_008};
    border-left: ${rem(5)} solid ${({ theme }) => theme.colors.green_008};
    border-radius: ${rem(3)};
  }
  .sendTime {
    display: inline-block;
    font-size: ${rem(11)};
    margin: 0 0 ${rem(2)} 0;
  }
`;

const ChatRoomProfileImg = styled.img`
  width: ${rem(46)};
  height: ${rem(46)};
  border-radius: ${rem(10)};
  margin-right: ${rem(7)};
`;

const DayDateBox = styled.div`
  width: ${rem(200)};
  height: ${rem(25)};
  border-radius: ${rem(15)};
  text-align: center;
  line-height: ${rem(25)};
  font-size: ${rem(13)};
  background-color: ${({ theme }) => theme.colors.beige_006};
  margin: ${rem(10)};
`;

export {
  DefualtContainer,
  ChatRoomHeaderContainer,
  ChatRoomBodyContainer,
  ChatRoomInputContainer,
  ChatRoomProfileImg,
  DayDateBox,
  ChatContainer,
  ChatContentListBox,
  ChatContentBox,
};
