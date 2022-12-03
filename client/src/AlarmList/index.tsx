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
  console.log(alarmListData);
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
