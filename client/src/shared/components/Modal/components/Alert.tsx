import ModalStore from 'store/store';
import { ModalViewBox, ModalbtnBox } from './ModalContainer';
import * as S from './SvgComponents';

interface Data {
  title: string;
  content: string;
  color: string;
  onClick: () => void;
}

const Alert = ({ data }: { data: Data }) => {
  const { CloseModal } = ModalStore();
  return (
    <ModalViewBox color={data.color}>
      <S.ApplySVG></S.ApplySVG>
      <h3>{data.title}</h3>
      <ModalbtnBox>
        <button onClick={CloseModal}>취소</button>
        <button className='Progressbtn' onClick={data.onClick}>
          {data.content}{' '}
        </button>
      </ModalbtnBox>
    </ModalViewBox>
  );
};

export { Alert };
