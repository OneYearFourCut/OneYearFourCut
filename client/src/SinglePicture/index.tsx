import Footer from 'shared/components/PicFooter/PicFooter';
import SinglePicture from './SinglePicture';
import styled from 'styled-components';
import { rem } from 'polished';
import React from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/pagination';
import './styles.css';
import useGetAllPost from '../shared/hooks/useGetAllPost';
import { useParams } from 'react-router-dom';

const Body = styled.div`
  width: ${rem(420)};
  height: auto;
  display: flex;
  flex-direction: column;
`;

const SinglePicPage = () => {
  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  const { data } = useGetAllPost(galleryId);

  // console.log(data?.data);

  return (
    <Body>
      <Swiper
        slidesPerView={1}
        spaceBetween={10}
        centeredSlides={true}
        className='swiper'
        pagination={false}
        initialSlide={0}
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
      </Swiper>
    </Body>
  );
};

export default SinglePicPage;
