import { rem } from 'polished';
import { useNavigate } from 'react-router-dom';
import { ModalStore, AlarmStore } from 'store/store';
import { AlarmCheckBox } from './HeaderBox';
import useReceiveAlarm from '../hook/useReceiveAlarm';

const HeaderBackbtn = () => {
  const navigate = useNavigate();

  return (
    <svg
      width={rem(10)}
      height={rem(14)}
      viewBox='0 0 10 14'
      fill='none'
      xmlns='http://www.w3.org/2000/svg'
      className='HeaderBackbtn'
      onClick={() => navigate(-1)}
    >
      <path
        d='M9.20985 0.513629L0.561412 6.77144C0.525477 6.79733 0.496211 6.8314 0.476025 6.87082C0.455839 6.91025 0.445312 6.95391 0.445312 6.9982C0.445312 7.04249 0.455839 7.08615 0.476025 7.12557C0.496211 7.165 0.525477 7.19906 0.561412 7.22496L9.20985 13.4828C9.39618 13.6164 9.65633 13.4828 9.65633 13.2543V11.6054C9.65633 11.2468 9.48407 10.9058 9.19227 10.6949L4.08055 6.99996L9.19227 3.30152C9.48407 3.09058 9.65633 2.75308 9.65633 2.39097V0.742144C9.65633 0.513629 9.39618 0.380035 9.20985 0.513629Z'
        fill='#316232'
      />
    </svg>
  );
};

const HeaderBellbtn = ({ isLoggedin }: { isLoggedin: boolean }) => {
  const { alarmIsOpen, onClick, data, status } = useReceiveAlarm(isLoggedin);

  return (
    <>
      {!alarmIsOpen && (
        <div className='HeaderBellbtn'>
          <svg
            width={rem(18)}
            height={rem(20)}
            viewBox='0 0 18 20'
            fill='none'
            xmlns='http://www.w3.org/2000/svg'
            onClick={onClick}
          >
            <path
              d='M9 20C9.6193 20.0008 10.2235 19.8086 10.7285 19.4502C11.2335 19.0917 11.6143 18.5849 11.818 18H6.182C6.38566 18.5849 6.76648 19.0917 7.27151 19.4502C7.77654 19.8086 8.3807 20.0008 9 20ZM16 12.586V8C16 4.783 13.815 2.073 10.855 1.258C10.562 0.52 9.846 0 9 0C8.154 0 7.438 0.52 7.145 1.258C4.185 2.074 2 4.783 2 8V12.586L0.293001 14.293C0.199958 14.3857 0.126171 14.4959 0.0758854 14.6172C0.0256001 14.7386 -0.000189449 14.8687 1.04767e-06 15V16C1.04767e-06 16.2652 0.105358 16.5196 0.292894 16.7071C0.480431 16.8946 0.734785 17 1 17H17C17.2652 17 17.5196 16.8946 17.7071 16.7071C17.8946 16.5196 18 16.2652 18 16V15C18.0002 14.8687 17.9744 14.7386 17.9241 14.6172C17.8738 14.4959 17.8 14.3857 17.707 14.293L16 12.586Z'
              fill='#D99441'
            />
          </svg>
          {data?.data.readAlarmExist && <AlarmCheckBox />}
        </div>
      )}
    </>
  );
};

const HeaderHamburgerbtn = () => {
  const { openModal } = ModalStore();
  const { alarmIsOpen } = AlarmStore();

  return (
    <>
      {!alarmIsOpen && (
        <svg
          width={rem(18)}
          height={rem(14)}
          viewBox='0 0 18 14'
          fill='none'
          xmlns='http://www.w3.org/2000/svg'
          className='HeaderHamburgerbtn'
          onClick={() => openModal('ProfileModal')}
        >
          <path
            fillRule='evenodd'
            clipRule='evenodd'
            d='M0.75 13C0.75 12.8011 0.829018 12.6103 0.96967 12.4697C1.11032 12.329 1.30109 12.25 1.5 12.25H16.5C16.6989 12.25 16.8897 12.329 17.0303 12.4697C17.171 12.6103 17.25 12.8011 17.25 13C17.25 13.1989 17.171 13.3897 17.0303 13.5303C16.8897 13.671 16.6989 13.75 16.5 13.75H1.5C1.30109 13.75 1.11032 13.671 0.96967 13.5303C0.829018 13.3897 0.75 13.1989 0.75 13ZM0.75 7C0.75 6.80109 0.829018 6.61032 0.96967 6.46967C1.11032 6.32902 1.30109 6.25 1.5 6.25H16.5C16.6989 6.25 16.8897 6.32902 17.0303 6.46967C17.171 6.61032 17.25 6.80109 17.25 7C17.25 7.19891 17.171 7.38968 17.0303 7.53033C16.8897 7.67098 16.6989 7.75 16.5 7.75H1.5C1.30109 7.75 1.11032 7.67098 0.96967 7.53033C0.829018 7.38968 0.75 7.19891 0.75 7ZM0.75 1C0.75 0.801088 0.829018 0.610322 0.96967 0.46967C1.11032 0.329018 1.30109 0.25 1.5 0.25H16.5C16.6989 0.25 16.8897 0.329018 17.0303 0.46967C17.171 0.610322 17.25 0.801088 17.25 1C17.25 1.19891 17.171 1.38968 17.0303 1.53033C16.8897 1.67098 16.6989 1.75 16.5 1.75H1.5C1.30109 1.75 1.11032 1.67098 0.96967 1.53033C0.829018 1.38968 0.75 1.19891 0.75 1Z'
            fill='#316232'
          />
        </svg>
      )}
    </>
  );
};

export { HeaderBackbtn, HeaderBellbtn, HeaderHamburgerbtn };
