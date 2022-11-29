import styled from 'styled-components';
import { rem } from 'polished';

const CommentBody = styled.div`
  width: inherit;
  display: flex;
  flex-direction: column;
  padding: ${rem(19)} ${rem(30)};
  z-index: -1;
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

export { CommentBody, PicTitle, Title, CommentCount };
