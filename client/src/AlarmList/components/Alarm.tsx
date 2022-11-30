import * as B from './AlarmContainer';
import { ALData, ALDataType } from 'AlarmList/types';
import { useNavigateSearch } from 'shared/hooks/useNavigateSearch';
const Alarm = ({ data }: { data: ALData }) => {
  const navigateSearch = useNavigateSearch();

  const makeContent = (data: ALData): string => {
    let content = `${data.userNickname}님이 `;
    switch (ALDataType[data.type]) {
      case ALDataType.LIKE_ARTWORK:
        content += `작품 < ${data.artworkTitle} >에 좋아요를 눌렀습니다.`;
        break;
      case ALDataType.COMMENT_ARTWORK:
        content += `작품 < ${data.artworkTitle} >에 댓글을 남겼습니다.`;
        break;
      case ALDataType.COMMENT_GALLERY:
        content += `전시관에 댓글을 남겼습니다.`;
        break;
      case ALDataType.POST_ARTWORK:
        content += `작품 < ${data.artworkTitle} >을 등록하셨습니다.`;
        break;
    }
    return content;
  };

  return (
    <B.AlarmBox
      read={data.read}
      onClick={() =>
        navigateSearch('/SinglePic', {
          artworkId: data.artworkId.toString(),
        })
      }
    >
      <B.DecorateBox read={data.read}>
        {/* 알람 왼쪽 데코부분 div 2개 */}
        <div></div>
        <div></div>
      </B.DecorateBox>
      <B.ContentBox read={data.read}>
        <ul>
          <li>{data.createdAt}</li>
          <li>{ALDataType[data.type]}</li>
          <li>{makeContent(data)}</li>
        </ul>
      </B.ContentBox>
    </B.AlarmBox>
  );
};

export default Alarm;
