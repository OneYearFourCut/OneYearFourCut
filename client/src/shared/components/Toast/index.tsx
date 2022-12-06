import * as B from './components/ToastContainer';
import { ToastStore } from 'store/store';
import ToastMessage from './components/ToastMessage';

//여러개의 Toast Message를 랜더링 하는곳
const ToastRender = () => {
  const { ToastList } = ToastStore();
  return (
    <B.ToastRenderBox>
      {ToastList.map((el) => (
        //key를 설정해주면 해당 key에 해당하는 값이 바뀌지않으면 리랜더링이 일어나지 않으므로 ToastList가 바뀌어도 기존에있던 Toast는 그대로 진행
        //key를 안적어주면 설정한 시간뒤에 해당 key를 가진 컴포넌트를 렌더링트리에서 삭제하라는 명령을 내렸는데 unshift에 의해 배열이 하나 줄어들어서 삭제되지 말아야 하는 컴포넌트가 삭제됨
        <ToastMessage
          time={el.time}
          content={[el.content[0], el.content[1]]}
          color={el.color}
          key={el.id}
        ></ToastMessage>
      ))}
    </B.ToastRenderBox>
  );
};

export default ToastRender;
