import * as B from './components/AlarmContainer';
import Alarm from './components/Alarm';
import Filter from './components/Filter';
import useHandleIntersection from './hooks/useHandleIntersection';
import { useEffect } from 'react';
import { AlarmStore } from 'store/store';

const AlarmList = () => {
  const { openAlarm, closeAlarm } = AlarmStore();
  const {
    isData,
    alarmListData,
    target,
    filter,
    setFilter,
    setPage,
    setAlarmListData,
    setIsData,
  } = useHandleIntersection();

  useEffect(() => {
    openAlarm();
    return () => {
      closeAlarm();
    };
  }, []);
  return (
    <>
      <B.DefualtContainer>
        <Filter
          filter={filter}
          setFilter={setFilter}
          setPage={setPage}
          setAlarmListData={setAlarmListData}
          setIsData={setIsData}
        />
        {!isData && <h4>현재 알람이 없습니다</h4>}
        {alarmListData.map((data) => (
          <Alarm key={data.alarmId} data={data}></Alarm>
        ))}
        {isData && (
          <B.TriggerBox ref={target}>
            <div />
          </B.TriggerBox>
        )}
      </B.DefualtContainer>
    </>
  );
};

export default AlarmList;
