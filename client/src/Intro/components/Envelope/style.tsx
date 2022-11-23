import React from 'react';
import { rem } from 'polished';
import styled from 'styled-components';

export const EnvelopeWrapper = styled.div`
  ${({ theme }) => theme.mixins.flexBox('column', 'center', 'center')};
  height: 100vh;
`;

export const Envelope = styled.div`
  width: ${rem(280)};
  height: ${rem(180)};
  border-bottom-left-radius: ${rem(6)};
  border-bottom-right-radius: ${rem(6)};
  margin: 0 auto;
  background-color: ${({ theme }) => theme.colors.red_006};
  box-shadow: 0 0.5rem 2.5rem rgba(0, 0, 0, 0.2);

  animation: swing ease-in-out 0.2s infinite alternate;
  transform-origin: center -2.5rem;
  float: left;
  box-shadow: ${rem(5)} ${rem(5)} ${rem(10)} rgba(0, 0, 0, 0.5);
  &.open .flap {
    transform: rotateX(180deg);
    transition: transform 0.4s ease, z-index 0.6s;
    z-index: 1;
  }

  &.close .flap {
    transform: rotateX(0deg);
    transition: transform 0.4s 0.6s ease, z-index 1s;
    z-index: 5;
  }
  &.open {
    animation-play-state: paused;
  }

  @keyframes swing {
    0% {
      transform: rotate(3deg);
    }
    100% {
      transform: rotate(-3deg);
    }
  }
`;

export const Front = styled.div`
  position: absolute;
  width: 0;
  height: 0;
  z-index: 3;

  &.flap {
    border-left: ${rem(140)} solid transparent;
    border-right: ${rem(140)} solid transparent;
    border-bottom: ${rem(82)} solid transparent;
    /* a little smaller */
    border-top: ${rem(98)} solid ${({ theme }) => theme.colors.red_006};
    /* a little larger */
    transform-origin: top;
    pointer-events: none;
  }

  &.pocket {
    border-left: ${rem(140)} solid ${({ theme }) => theme.colors.red_001};
    border-right: ${rem(140)} solid ${({ theme }) => theme.colors.red_001};
    border-bottom: ${rem(90)} solid ${({ theme }) => theme.colors.red_007};
    border-top: ${rem(90)} solid transparent;
    border-bottom-left-radius: ${rem(6)};
    border-bottom-right-radius: ${rem(6)};
  }
`;
