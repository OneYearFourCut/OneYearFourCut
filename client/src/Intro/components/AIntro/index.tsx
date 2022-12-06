import * as S from './style';

const Index = () => {
  return (
    <>
      <S.Container>
        <h1>올해 네 컷을 만든 D25는 어떤 팀?</h1>
        <S.divContainer>
          <div>🔸 코드스테이츠 메인 프로젝트 미디어 산업군이에요!</div>
          <div>🔸 프론트엔드 개발자 3명과 백엔드 개발자 3명이에요!</div>
          <div>🔸 목표는 트래픽이 터질만한 멋진 프로젝트를 만들어보자!</div>
        </S.divContainer>
        <S.FourCut>
          <S.Frame src='https://image.rocketpunch.com/company/19718/code-states_logo_1604475831.png?s=400x400&t=inside' />
          <S.Frame src='https://images.unsplash.com/photo-1580741569354-08feedd159f9?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80' />
          <S.Frame src='https://images.unsplash.com/photo-1611162616305-c69b3fa7fbe0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1074&q=80' />
          <S.Frame src='https://images.unsplash.com/photo-1608371945786-d47d3cdd31da?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80' />
        </S.FourCut>
      </S.Container>
    </>
  );
};

export default Index;
