import { useState } from 'react';
import { ALDataType, ALData } from 'AlarmList/types';
import * as B from './FilterContainer';
const Filter = ({
  filter,
  setFilter,
  setPage,
  setAlarmListData,
  setIsData,
}: {
  filter: string;
  setFilter: (innerHTML: string) => void;
  setPage: (num: number) => void;
  setAlarmListData: (alarmListData: ALData[]) => void;
  setIsData: (isData: boolean) => void;
}) => {
  const [isOpen, setIsOpen] = useState(false);

  const handleOnClick = (innerHTML: string) => {
    setIsOpen(!isOpen);
    setFilter(innerHTML);
    setPage(1);
    setAlarmListData([]);
    setIsData(true);
  };

  return (
    <>
      <B.FilterBox isOpen={isOpen}>
        <label>{ALDataType[filter]}</label>
        <div className='DropDownBtn' onClick={() => setIsOpen(!isOpen)}>
          &gt;
        </div>
      </B.FilterBox>
      {isOpen && (
        <B.FilterListBox>
          <div>
            <ul>
              {Object.keys(ALDataType).map((key, idx) => (
                <li key={idx} onClick={() => handleOnClick(key)}>
                  {ALDataType[key]}
                </li>
              ))}
            </ul>
          </div>
        </B.FilterListBox>
      )}
    </>
  );
};

export default Filter;
