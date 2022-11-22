import styled from 'styled-components';
import CommentsList from './CommentsZone';
import { rem } from 'polished';
import CommentInput from './CommentInput';

const Body = styled.div`
  width: ${rem('428px')};
  /* height: ${rem('926px')}; */
  height: 100vh;
  /* max-height: ${rem('926px')}; */
  max-width: ${rem(540)};
  padding-top: 50px;
  overflow-x: hidden;
  overflow-y: scroll;
  background-color: ${({ theme }) => theme.colors.black_007};
`;

const SingleComment = () => {
  return (
    <Body>
      <CommentsList></CommentsList>
      <CommentInput></CommentInput>
    </Body>
  );
};

export default SingleComment;
