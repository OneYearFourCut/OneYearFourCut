import styled from 'styled-components';
import { rem } from 'polished';
import { ApplySVG } from 'shared/components/Modal/components/SvgComponents';
import { useNavigate, useParams } from 'react-router-dom';

const Body = styled.div`
  ${({ theme }) => theme.flex.center}
  flex-direction: column;
  width: ${rem(327)};
  height: ${rem(170)};
  background-color: white;
  border-radius: ${rem(10)};
  z-index: 52;
  position: relative;
  .ApplySVG {
    position: absolute;
    top: 0%;
    left: 50%;
    transform: translate(-50%, -50%);
  }
`;

const ButtonBox = styled.div`
  ${({ theme }) => theme.flex.center}
  width: ${rem(259)};
  height: ${rem(34)};
  margin-top: ${rem(35)};
  display: flex;
  flex-direction: column;

  button {
    width: ${rem(236)};
    height: ${rem(34)};
    border-radius: ${rem(5)};
    border: none;
    margin: ${rem(4)};
    padding: ${rem(4)};
    font-weight: 400;
    background-color: ${({ theme }) => theme.colors.green_002};
    cursor: pointer;
    color: ${({ theme }) => theme.colors.black_007};
    border: 1px solid ${({ theme }) => theme.colors.green_002};
  }
`;

const LastPageComponent = () => {
  const navigate = useNavigate();
  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  const GoComments = () => {
    navigate(`/allPic/${galleryId}/artworks/comments`);
  };
  const GoUpload = () => {
    navigate(`/uploadPicture`);
  };

  return (
    <Body>
      <ApplySVG />
      <h3>전시를 모두 관람하셨습니다</h3>
      <ButtonBox>
        <button onClick={() => GoComments()}>방명록 보기</button>
        <button onClick={() => GoUpload()}>나도 작품 등록하기</button>
      </ButtonBox>
    </Body>
  );
};

export default LastPageComponent;
