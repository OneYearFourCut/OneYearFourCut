import styled from 'styled-components';
import CommentsList from './CommentsZone';
import { rem } from 'polished';
import CommentInput from './CommentInput';

const Body = styled.div`
  width: 100%;
  height: 100vh;
  padding-top: 50px;
  overflow-x: hidden;
  overflow-y: scroll;
  background-color: rgba(0, 0, 0, 0.3);

  display: flex;
  justify-content: end;
`;

const RoundBody = styled.div`
  width: 100%;
  height: fit-content;
  background-color: ${({ theme }) => theme.colors.black_007};
  border-radius: ${rem(30)} ${rem(30)} 0 0;
`;

const SingleComment = () => {
  return (
    <Body>
      <RoundBody>
        <CommentsList />
        <CommentInput />
      </RoundBody>
    </Body>
  );
};

export default SingleComment;
