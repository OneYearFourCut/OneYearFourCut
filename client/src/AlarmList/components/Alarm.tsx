import * as B from './AlarmContainer';

interface Data {
  createAt: string;
  title: string;
  content: string;
  read: boolean;
}

const Alarm = ({data} : {data:Data})=>{

    return (
      <B.AlarmBox read = {data.read}>
        <B.DecorateBox read = {data.read}>
            {/* 알람 왼쪽 데코부분 div 2개 */}
            <div></div>
            <div></div>
        </B.DecorateBox>
          <B.ContentBox read = {data.read}>
          <ul>
            <li>{data.createAt}</li>
            <li>{data.title}</li>
            <li>{data.content}</li>
          </ul>
        </B.ContentBox>
      </B.AlarmBox>
    );
}

export default Alarm;