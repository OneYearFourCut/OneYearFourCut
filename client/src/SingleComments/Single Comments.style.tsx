import styled from 'styled-components';
import { rem } from 'polished';

const CommentBody = styled.div`
  width: inherit;
  display: flex;
  flex-direction: column;
  padding: ${rem(19)} ${rem(30)};
  z-index: -1;
  margin-bottom: ${rem(150)};
  min-height: 80vh;
  height: fit-content;
`;

const PicTitle = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-bottom: ${rem(24)};
  color: ${({ theme }) => theme.colors.green_002};
`;

const Title = styled.div`
  font-style: normal;
  font-weight: 700;
  font-size: ${rem(20)};
  line-height: ${rem(27)};
`;

const CommentCount = styled.div`
  font-style: normal;
  font-weight: 700;
  font-size: ${rem(20)};
  line-height: ${rem(27)};
`;

const NoComment = styled.div`
  font-style: normal;
  font-weight: 400;
  font-size: ${rem(14)};
  color: ${({ theme }) => theme.colors.gray_004};
  margin-left: auto;
  margin-right: auto;
  padding-top: ${rem(32)};
`;
export { CommentBody, PicTitle, Title, CommentCount, NoComment };
