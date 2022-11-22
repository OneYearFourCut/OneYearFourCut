import * as S from './SvgComponents';
import { ModalStore, ToastStore } from 'store/store';
import { ModalViewBox, ModalbtnBox } from './ModalContainer';

interface Data {
  title: string;
  content: string;
  color: string;
  onClick: () => void;
}

const Alert = ({ data }: { data: Data }) => {
  const { closeModal } = ModalStore();
  const { addToast, removeToast } = ToastStore();

  const handleOnClick = () => {
    closeModal('AlertModal');

    const obj = {
      time: 3000, //ms
      content: ['작품이 등록되었습니다.', '내 전시관도 만들어보기'], //위,아래에 들어갈 원하는 내용 작성
      id: Math.random(), //key값을 위함. uuid를 써도됨
    };
    addToast(obj); //ToastStore에 Toast 추가
    setTimeout(() => removeToast(), 3000); //ToastStroe에서 만든 Toast요소제거
  };

  return (
    <ModalViewBox color={data.color}>
      <S.ApplySVG></S.ApplySVG>
      <h3>{data.title}</h3>
      <ModalbtnBox>
        <button onClick={() => closeModal('AlertModal')}>취소</button>
        <button className='Progressbtn' onClick={handleOnClick}>
          {data.content}{' '}
        </button>
      </ModalbtnBox>
    </ModalViewBox>
  );
};

export { Alert };
