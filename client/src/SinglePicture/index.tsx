import Footer from 'shared/components/PicFooter/PicFooter';
import SinglePicture from './SinglePicture';
import styled from 'styled-components';
import { rem } from 'polished';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/pagination';
import 'swiper/css/navigation';
import './styles.css';
import { Navigation } from 'swiper';
import useGetAllPost from '../shared/hooks/useGetAllPost';
import { useLocation, useParams } from 'react-router-dom';
import LastPageComponent from './OnePage/LastPageComponent';
import CommentStore from 'store/store';

const Body = styled.div`
  width: ${rem(428)};
  height: 95vh;
  display: flex;
  flex-direction: column;
`;

const LastPage = styled.div`
  width: 100%;
  height: 100vh;
  background-color: ${({ theme }) => theme.colors.black_005};
  display: flex;
  justify-content: center;
  align-items: center;
`;

const SinglePicPage = () => {
  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  const { data } = useGetAllPost(galleryId);
  const { state } = useLocation();
  const { lastOpen } = CommentStore();

  let num = 0;
  if (state !== null && lastOpen === -1) {
    num = state;
  }
  if (lastOpen !== -1) {
    num = lastOpen;
  }

  const setting = {
    slidesPerView: 1,
    centeredSlides: true,
    pagination: false,
    initialSlide: num,
  };

  return (
    <Body>
      <Swiper
        {...setting}
        modules={[Navigation]}
        className='swiper one'
        navigation
      >
        {data &&
          data.data.map((el: any, idx: number, array: any) => (
            <SwiperSlide className='swiper-slide' key={el.artworkId}>
              <div className='single'>
                <SinglePicture
                  idx={idx}
                  array={array.length}
                  picture={el.imagePath}
                  title={el.title}
                  scrpit={el.content}
                  username={el.memberId}
                  artId={el.artworkId}
                  nickname={el.nickName}
                ></SinglePicture>
                <Footer
                  like={el.likeCount}
                  comment={el.commentCount}
                  artworkId={el.artworkId}
                  galleryId={galleryId}
                  idx={idx}
                ></Footer>
              </div>
            </SwiperSlide>
          ))}
        <SwiperSlide className='swiper-slide'>
          <LastPage>
            <LastPageComponent />
          </LastPage>
        </SwiperSlide>
      </Swiper>
    </Body>
  );
};

export default SinglePicPage;
