import DynamicExample from '../DynamicExample';
import * as S from './style';

const Index = () => {
  return (
    <>
      <S.Container>
        <h1>왜 올해 네 컷인가요?</h1>
        <S.divContainer>
          <h3>우리의 2022년을 전시해보자!</h3>
          <div>🔸 왜 인스타그램에서 유형 테스트가 인기 있을까?</div>
          <div>
            🔸 사람들은 다른 사람들이 보는 나에 대한 니즈가 있는 게 아닐까?
          </div>
          <div>
            🔸 그렇다면 다른 사람들에게 내 2022년을 전시해달라고 해보면 어떨까?
          </div>
        </S.divContainer>
        <DynamicExample />
      </S.Container>
    </>
  );
};

export default Index;
