import type { IChat, IChatServerData, IChatData } from 'Chatroom/types';

export const handleData = (
  serverData: IChatServerData[],
  processedData: IChatData[], //서버에서 받은 데이터를 가공하는걸 담는 배열
  memberId: number,
) => {
  const checkDataListAtdayDate = (dataList: IChatData[], dayDate: string) => {
    //dataList에 해당날짜가 존재하면 해당 인덱스 리턴, 존재하지 않으면 false
    for (let i = dataList.length - 1; i >= 0; i--) {
      if (dataList[i].dayDate === dayDate) return i;
    }
    return false;
  };

  const makeChatListObject = (data: IChatServerData): IChat => {
    let time = data.createdAt.split('T')[1];
    return {
      content: [data.message],
      time,
      img: data.profile,
      nickName: data.nickname,
      type: memberId === data.memberId ? 'right' : 'left',
    };
  };

  // 가장 최신에 보낸 메세지(마지막인덱스)의 memberId와 비교해서 다르면 chatList에 넣고 같으면 다음 조건문 실행
  // 같은 시간, 분 을가진 것과 같은 type을 가진게 있는지 판단.
  const addDataListOrContent = (chatList: IChat[], data: IChatServerData) => {
    let time = data.createdAt.split('T')[1];
    let type = memberId === data.memberId ? 'right' : 'left';
    if (chatList[chatList.length - 1].type !== type) {
      chatList.push(makeChatListObject(data));
    } else {
      for (let i = chatList.length - 1; i >= 0; i--) {
        if (chatList[i].type === type && chatList[i].time === time) {
          chatList[i].content.push(data.message);
          return;
        }
      }
      chatList.push(makeChatListObject(data));
    }
  };

  const day = [
    '일요일',
    '월요일',
    '화요일',
    '수요일',
    '목요일',
    '금요일',
    '토요일',
  ];

  for (let i = serverData.length - 1; i >= 0; i--) {
    let date = new Date(serverData[i].createdAt);
    let dayDate = `${date.getFullYear()}년 ${
      date.getMonth() + 1
    }월 ${date.getDate()}일 ${day[date.getDay()]}`;

    let checkResult = checkDataListAtdayDate(processedData, dayDate);

    if (checkResult === false) {
      processedData.push({
        dayDate: dayDate,
        chatList: [makeChatListObject(serverData[i])],
      });
    } else {
      if (
        processedData[checkResult].chatList[0].time ===
        serverData[i].createdAt.split('T')[1]
      )
        processedData[checkResult].chatList[0].content.push(
          serverData[i].message,
        );
      else
        addDataListOrContent(
          processedData[checkResult].chatList,
          serverData[i],
        );
    }
  }
  //useState값인 processedData를 매개변수로 받았기 때문에 그대로 setState를 하면 배열이 안변한줄알아서 업데이트가 안된다. 따라서 새로운 배열을 할당해줘야한다.
  return [...processedData];
};
