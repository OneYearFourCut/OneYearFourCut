import * as B from './AlarmContainer';
import { type ALData, ALDataType } from 'AlarmList/types';
import { useNavigateSearch } from 'shared/hooks/useNavigateSearch';
const Alarm = ({ data }: { data: ALData }) => {
  const navigateSearch = useNavigateSearch();

  const handleData = (data: ALData): { content: string; url: string } => {
    let content = `${data.userNickname}님이 `;
    let url = '/allPic';
    switch (ALDataType[data.alarmType]) {
      case ALDataType.LIKE_ARTWORK:
        content += `작품 < ${data.artworkTitle} >에 좋아요를 눌렀습니다.`;
        url += `/${data.galleryId}/${data.artworkId}`;
        break;
      case ALDataType.COMMENT_ARTWORK:
        content += `작품 < ${data.artworkTitle} >에 댓글을 남겼습니다.`;
        url += `/${data.galleryId}/${data.artworkId}/comments`;
        break;
      case ALDataType.COMMENT_GALLERY:
        content += `전시관에 댓글을 남겼습니다.`;
        url += `/${data.galleryId}/comments`;
        break;
      case ALDataType.REPLY_ARTWORK:
        content += `작품 < ${data.artworkTitle} >에 남긴 댓글에 댓글이 등록되었습니다.`;
        url += `/${data.galleryId}/${data.artworkId}/comments`;
        break;
      case ALDataType.REPLY_GALLERY:
        content += `전시관 에 남긴 댓글에 댓글이 등록되었습니다.`;
        url += `/${data.galleryId}/comments`;
        break;
      case ALDataType.POST_ARTWORK:
        content += `작품 < ${data.artworkTitle} >을 등록하셨습니다.`;
        url += `/${data.galleryId}/${data.artworkId}`;
        break;
      default:
        content = '오류';
        url += `/`;
        break;
    }
    return { content, url };
  };

  return (
    <B.AlarmBox
      read={data.read}
      onClick={() => navigateSearch(handleData(data).url, {})}
    >
      <B.DecorateBox read={data.read}>
        {/* 알람 왼쪽 데코부분 div 2개 */}
        <div></div>
        <div></div>
      </B.DecorateBox>
      <B.ContentBox read={data.read}>
        <ul>
          <li>{data.createdAt.replace('T', ' ')}</li>
          <li>{ALDataType[data.alarmType]}</li>
          <li>{handleData(data).content}</li>
        </ul>
      </B.ContentBox>
    </B.AlarmBox>
  );
};

export default Alarm;
