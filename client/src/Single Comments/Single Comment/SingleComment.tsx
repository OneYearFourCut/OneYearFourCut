import styled from 'styled-components';
import { rem } from 'polished';

const Body = styled.div`
  height: ${rem(114)};
  border-bottom: ${rem(1)} solid ${({ theme }) => theme.colors.black_005};
  display: flex;
  flex-direction: column;
`;

const Info = styled.div`
  margin: ${rem(8)} 0;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
`;

const NickName = styled.div`
  font-style: normal;
  font-weight: 500;
  font-size: ${rem(16)};
  line-height: ${rem(22)};
`;

const Time = styled.div`
  font-style: normal;
  font-weight: 400;
  font-size: ${rem(12)};
  line-height: ${rem(16)};
  color: ${rem(1)} solid ${({ theme }) => theme.colors.black_003};
`;

const Comment = styled.div`
  font-style: normal;
  font-weight: 400;
  font-size: ${rem(12)};
  line-height: ${rem(16)};
  color: ${rem(1)} solid ${({ theme }) => theme.colors.black_001};
  margin: ${rem(8)} 0;
`;

const ButtonZone = styled.div`
  display: flex;
  justify-content: right;
  margin-top: auto;
  margin-bottom: ${rem(8)};
`;

const Button = styled.button`
  width: ${rem(52)};
  height: ${rem(18)};
  border-radius: ${rem(25)};
  border-color: ${({ theme }) => theme.colors.black_004};
  background-color: ${({ theme }) => theme.colors.black_007};
  outline: 0;
  box-shadow: none;

  font-style: normal;
  font-weight: 300;
  font-size: ${rem(9)};
  line-height: ${rem(12)};
  text-align: center;
  color: ${({ theme }) => theme.colors.black_002};
`;

const SingleComment = ({
  nickname,
  time,
  comment,
}: {
  nickname: string;
  time: number;
  comment: string;
}) => {
  return (
    <Body>
      <Info>
        <NickName>{nickname}</NickName>
        <Time>{time}</Time>
      </Info>
      <Comment>{comment}</Comment>
      <ButtonZone>
        <Button type='button'>답글</Button>
      </ButtonZone>
    </Body>
  );
};

export default SingleComment;
