import * as S from './style';
export const ChatContent = ({ content }: { content: string }) => {
  return (
    <S.ChatContentBox>
      <div>{content}</div>
    </S.ChatContentBox>
  );
};
