import styled from 'styled-components';
import { rem } from 'polished';
import CommentInput from './CommentInput';
import { Suspense } from 'react';
import React from 'react';
const CommentsList = React.lazy(() => import('./CommentsZone'));

const Body = styled.div`
  width: 100%;
  max-width: ${rem(428)};
  height: 100vh;
  padding-top: 50px;
  overflow-x: hidden;
  overflow-y: scroll;
  background-color: rgba(0, 0, 0, 0.3);

  display: flex;
  justify-content: end;
  position: relative;
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none; /* Firefox */
  ::-webkit-scrollbar {
    display: none; /* Chrome, Safari, Opera*/
  }
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
        <Suspense fallback={<div>Loading...</div>}>
          <CommentsList />
        </Suspense>
        <CommentInput />
      </RoundBody>
    </Body>
  );
};

export default SingleComment;
