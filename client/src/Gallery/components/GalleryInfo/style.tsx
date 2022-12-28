import styled from 'styled-components';
import { rem } from 'polished';

export const Info = styled.div`
  ${({ theme }) => theme.mixins.flexBox('row', 'center', 'flex-start')}
  width: ${rem(428)};
  padding: ${rem(20)};
`;

export const ProfileBox = styled.div`
  ${({ theme }) => theme.mixins.flexBox('column', 'center', 'center')}
  width: ${rem(120)};
  margin-left: ${rem(10)};
`;
export const ProfileCircle = styled.div`
  width: ${rem(50)};
  height: ${rem(50)};
  border-radius: 50%;
  overflow: hidden;
`;

export const Profile = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;

export const InfoBox = styled.div`
  ${({ theme }) => theme.mixins.flexBox('column', 'flex-start', 'center')}
`;

export const Title = styled.h2`
  text-align: left;
`;

export const Content = styled.div`
  text-align: left;
`;
