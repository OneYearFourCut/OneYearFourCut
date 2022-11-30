import styled from 'styled-components';
import { rem } from 'polished';

export const Info = styled.div`
  ${({ theme }) => theme.mixins.flexBox}
  margin-bottom: ${rem(10)};
`;

export const ProfileBox = styled.div`
  width: ${rem(50)};
  height: ${rem(50)};
  border-radius: 50%;
  overflow: hidden;
  margin-right: ${rem(20)};
`;
export const Profile = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;
