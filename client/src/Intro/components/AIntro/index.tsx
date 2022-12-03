import * as S from './style';

const Index = () => {
  return (
    <>
      <S.Container>
        <h1>여러분의 올 한 해는 어떠셨나요?</h1>

        <S.FourCut>
          <S.Frame src='https://images.unsplash.com/photo-1522778119026-d647f0596c20?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80' />
          <S.Frame src='https://images.unsplash.com/photo-1541199249251-f713e6145474?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1074&q=80' />
          <S.Frame />
          <S.Frame />
        </S.FourCut>
      </S.Container>
    </>
  );
};

export default Index;
