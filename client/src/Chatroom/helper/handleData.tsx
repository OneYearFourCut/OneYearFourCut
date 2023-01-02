import type { IChat, IChatServerData, IChatData } from 'ChatRoom/types';

export const handleData = (
  serverData: IChatServerData[],
  processedData: IChatData[], //서버에서 받은 데이터를 가공하는걸 담는 배열
  senderId: number,
) => {
  const checkDataListAtdayDate = (dataList: IChatData[], dayDate: string) => {
    for (let i = dataList.length - 1; i >= 0; i--) {
      if (dataList[i].dayDate === dayDate) return i;
    }
    return false;
  };

  //넣을 데이터 가공하는곳
  const makeChatListObject = (serverData: IChatServerData): IChat => {
    let time = serverData.createdAt.split('T')[1];
    return {
      content: [serverData.message],
      time,
      img: serverData.profile,
      nickName: serverData.nickname,
      type: typeDecision(senderId, serverData.senderId),
    };
  };

  // 가장 최신에 보낸 메세지(마지막인덱스)의 senderId와 비교해서 다르면 chatList에 넣고 같으면 다음 조건문 실행
  // 같은 시간, 분 을가진 것과 같은 type을 가진게 있는지 판단.
  const addDataListOrContent = (chatList: IChat[], data: IChatServerData) => {
    let time = data.createdAt.split('T')[1];

    let type = typeDecision(senderId, data.senderId);

    //마지막으로 보낸 메세지가 상대방이라면
    if (chatList[chatList.length - 1].type !== type) {
      chatList.push(makeChatListObject(data));
    }

    //마지막으로 보낸 메세지가 내가보낸것인것중에
    else {
      // 가장 최신의 메세지의 시간이 보낸시간과 같으면 해당 content에 push
      if (chatList[chatList.length - 1].time === time) {
        chatList[chatList.length - 1].content.push(data.message);
      }
      // 가장 최신의 메세지의 시간이 보낸시간과 다르면 새로운 chatList 생성(push)
      else chatList.push(makeChatListObject(data));
    }
  };

  const typeDecision = (memberId: number, senderId: number) => {
    return memberId === senderId ? 'right' : 'left';
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

  //실시간 채팅으로 받는 데이터는 배열의 크기가 1이므로 한번만 작동
  for (let i = serverData.length - 1; i >= 0; i--) {
    let date = new Date(serverData[i].createdAt);
    let dayDate = `${date.getFullYear()}년 ${
      date.getMonth() + 1
    }월 ${date.getDate()}일 ${day[date.getDay()]}`;

    let checkResult = checkDataListAtdayDate(processedData, dayDate);
    //dataList에 해당날짜가 존재하면 해당 '인덱스' 리턴, 존재하지 않으면 false
    if (checkResult === false) {
      processedData.push({
        dayDate: dayDate,
        chatList: [makeChatListObject(serverData[i])],
      });
    }
    //새로운 날짜에 넣음
    else {
      addDataListOrContent(processedData[checkResult].chatList, serverData[i]);
    }
  }
  //useState값인 processedData를 매개변수로 받았기 때문에 그대로 setState를 하면 배열이 안변한줄알아서 업데이트가 안된다. 따라서 새로운 배열을 할당해줘야한다.
  return [...processedData];
};

/*
[
  {
    dayDate: '2022년 12월 22일 월요일',
    chatList: {
                content: ['test내용1' 'test내용2'],
                time: '10:20',
                img: '이미지 경로',
                nickName: '닉네임',
                type: 'left or right',
              },
  },
] 
*/
