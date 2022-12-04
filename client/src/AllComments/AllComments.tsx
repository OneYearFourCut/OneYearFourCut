import styled from 'styled-components';
import AllCommentsList from './AllCommentList';
import { rem } from 'polished';
import CommentAllInput from './AllCommentInput';

const Body = styled.div`
  width: 100%;
  height: 100vh;
  padding-top: 50px;
  overflow-x: hidden;
  overflow-y: scroll;
  background-color: rgba(0, 0, 0, 0.3);
  width: 100%;
  max-width: ${rem(428)};
  display: flex;
  justify-content: end;
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

const AllComments = () => {
  return (
    <Body>
      <RoundBody>
        <AllCommentsList />
        <CommentAllInput />
      </RoundBody>
    </Body>
  );
};

export default AllComments;
