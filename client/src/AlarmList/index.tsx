import * as B from './components/AlarmContainer';
import Alarm from './components/Alarm';
import Filter from './components/Filter';
import { AlarmStore } from 'store/store';
const AlarmList = () => {
  
  const { isOpen } = AlarmStore();

  let data = [
    {
      createAt: '2022-11-21 오전 10:14',
      title: '작품 댓글 등록',
      content: 'OO님이 작품 <ㄹㅈ두러ㅏ줃>에 댓글을 등록했습니다.',
      read: true,
    },
    {
      createAt: '2022-11-15 오전 10:14',
      title: '전시관 댓글 등록',
      content:
        'OO님이 전시관에 댓글을 asdaㄴㅇㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㅂㅈㄷㄹㄴㅇㄹㄴㅇㄹㄴㅇㄹㄴㅇㄹㄴㅇㄹgsdfgsdfgsdfgsdgfgfgfqweqweq qwerwerwerwqwe dfsdfs sdfsdfsdfsdfqweqweqweqweqweqweqwe ewrwerwerwer q weqweqweqweqw eqw eqweqw',
      read: false,
    },
  ];

  return (
    <>
      {isOpen ? (
        <B.DefualtContainer>
          <Filter />
          {data.map((data) => (
            <Alarm data={data}></Alarm>
          ))}
        </B.DefualtContainer>
      ) : null}
    </>
  );
};

export default AlarmList;
