import * as S from './style';
import { Btn } from 'shared/components/Buttons';
import { StyledLink } from 'shared/components/LinkButton/style';
import { loginStore } from 'store/store';
import { useLikeData } from 'Gallery/hooks/useLikeData';

const Index = () => {
  const { user } = loginStore();
  const galleryId = user?.galleryId!;
  const { data } = useLikeData(galleryId!);

  return (
    <S.Container>
      <S.FourCut>
        {data?.map((like: any) => (
          <S.Frame
            key={like.artworkId}
            className='box'
            src={like.imagePath}
          ></S.Frame>
        ))}
      </S.FourCut>
      <S.BtnContainer>
        <Btn className='square'>
          <StyledLink to='/allPic' className='white'>
            전체 작품 보기
          </StyledLink>
        </Btn>
        <Btn className='square white'>
          <StyledLink to='/'>나도 전시관 만들기</StyledLink>
        </Btn>
      </S.BtnContainer>
      <div>
        <p>여러분만의 1년이 담긴 전시회도 만들고</p>
        <p>친구들에게 공유해보세요!</p>
      </div>
    </S.Container>
  );
};

export default Index;
