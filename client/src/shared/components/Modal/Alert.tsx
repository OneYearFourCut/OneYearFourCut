import * as S from './components/SvgComponents';
import { ModalStore } from 'store/store';
import { ModalViewBox, ModalbtnBox } from './components/ModalContainer';

interface Data {
  title: string;
  content: string;
  color: string;
  target: string;
  onClick: () => void;
}

const Alert = ({ data }: { data: Data }) => {
  const { closeModal } = ModalStore();

  return (
    <ModalViewBox color={data.color}>
      <S.ApplySVG></S.ApplySVG>
      <h3>{data.title}</h3>
      <ModalbtnBox>
        <button onClick={() => closeModal(data.target)}>취소</button>
        <button className='Progressbtn' onClick={data.onClick}>
          {data.content}{' '}
        </button>
      </ModalbtnBox>
    </ModalViewBox>
  );
};

export { Alert };
