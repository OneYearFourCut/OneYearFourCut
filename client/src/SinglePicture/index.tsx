import Footer from 'shared/components/PicFooter/PicFooter';
import SinglePicture from './SinglePicture';

import styled from 'styled-components';
import { rem } from 'polished';
import React from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/pagination';
import './styles.css';

import CommentStore from 'shared/components/PicFooter/OpenComment';
import SingleComment from 'SingleComments/index';

import useGetAllPost from '../shared/hooks/useGetAllPost';

import ModalBackdrop from 'shared/components/Modal/components/ModalBackdrop';

const Body = styled.div`
  width: ${rem(420)};
  height: auto;
  display: flex;
  flex-direction: column;
`;

const SinglePicPage = () => {
  const { open } = CommentStore();
  const { data } = useGetAllPost(17);

  return (
    <Body>
      {open ? (
        <ModalBackdrop>
          <SingleComment />
        </ModalBackdrop>
      ) : (
        <Swiper
          slidesPerView={1}
          spaceBetween={10}
          centeredSlides={true}
          className='swiper'
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
                  ></Footer>
                </Body>
              </SwiperSlide>
            ))}
        </Swiper>
      )}
    </Body>
  );
};

export default SinglePicPage;
