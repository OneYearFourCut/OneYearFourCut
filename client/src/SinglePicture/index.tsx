import Footer from 'shared/components/PicFooter/PicFooter';
import SinglePicture from './SinglePicture';
import styled from 'styled-components';
import { rem } from 'polished';
import React, { useEffect, useState } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/pagination';
import './styles.css';
import useGetAllPost from '../shared/hooks/useGetAllPost';
import { useLocation, useParams } from 'react-router-dom';
import CommentStore from 'shared/components/PicFooter/OpenComment';
import LastPageComponent from './OnePage/LastPageComponent';


const Body = styled.div`
  width: ${rem(420)};
  height: auto;
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
  const [swiper, setSwiper] = useState(0);
  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  const { data } = useGetAllPost(galleryId);

  const { lastOpen, setLastOpen } = CommentStore();
  const num = 1;
  let Last = data?.data[data.data.length - 1];

  const setting = {
    slidesPerView: 1,
    spaceBetween: 10,
    centeredSlides: true,
    pagination: false,
    initialSlide: lastOpen,
  };

  useEffect(() => {
    setLastOpen(swiper);
  }, [swiper]);
   const { state } = useLocation(); // << artworkId입니다!

  return (
    <Body>
      <Swiper
        {...setting}
        className='swiper one'
        onSlideChange={(e) => setSwiper(e.activeIndex)}
      >
        {data &&
          data.data.map((el: any, idx: number, array: any) => (
            <SwiperSlide className='swiper-slide' key={el.artworkId}>
              <Body className='single'>
                <SinglePicture
                  idx={idx}
                  array={array.length}
                  picture={el.imagePath}
                  title={el.title}
                  scrpit={el.content}
                  username={el.memberId}
                  artId={el.artworkId}
                ></SinglePicture>
                <Footer
                  like={el.likeCount}
                  comment={el.commentCount}
                  artworkId={el.artworkId}
                  galleryId={galleryId}
                ></Footer>
              </Body>
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

// {/* <Body className='single'>
//   <SinglePicture
//     idx={data?.data.length - 1}
//     array={data?.data.length}
//     picture={Last.imagePath}
//     title={Last.title}
//     scrpit={Last.content}
//     username={Last.memberId}
//     artId={Last.artworkId}
//   ></SinglePicture>
//   <Footer
//     like={Last.likeCount}
//     comment={Last.commentCount}
//     artworkId={Last.artworkId}
//     galleryId={galleryId}
//   ></Footer>
// // </Body> */}
