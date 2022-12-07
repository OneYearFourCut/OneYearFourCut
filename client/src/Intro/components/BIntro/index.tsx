import * as S from './style';

const Index = () => {
  return (
    <>
      <S.Container>
        <h1>왜 올해 네 컷인가요?</h1>
        <S.divContainer>
          <h2>우리의 2022년을 전시해보자!</h2>
          <div>🔸 왜 인스타그램에서 유형 테스트가 인기 있을까?</div>
          <div>🔸 사람들은 다른 사람들이 보는 나에 대한 니즈가 있다!</div>
          <div>🔸 다른 사람이 나의 2022년으로 전시관을 꾸며주는 </div>
          <div> 서비스는 어떨까?</div>
        </S.divContainer>
        <S.FourCut>
          <S.Frame src='https://images.unsplash.com/photo-1608371945786-d47d3cdd31da?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80' />
          <S.Frame src='https://images.unsplash.com/photo-1641945512297-538a81b13f85?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80' />
          <S.Frame src='https://images.unsplash.com/photo-1522134239946-03d8c105a0ba?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80' />
          <S.Frame src='https://images.unsplash.com/photo-1611926653670-e18689373857?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80' />
        </S.FourCut>
      </S.Container>
    </>
  );
};

export default Index;
