import styled from 'styled-components';
import { rem } from 'polished';

export const chatContainer = styled.div`
  ${({ theme }) => theme.mixins.flexBox('row', 'center', 'flex-start')}
  width: ${rem(428)};
  height: ${rem(90)};
  margin-top: ${rem(10)};
  background-color: ${({ theme }) => theme.colors.black_010};
`;

export const ProfileBox = styled.div`
  ${({ theme }) => theme.mixins.flexBox('column', 'center', 'center')}
  width: ${rem(120)};
  height: 100%;
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
  min-width: ${rem(248)};
  height: 100%;
  ${({ theme }) => theme.mixins.flexBox('column', 'flex-start', 'center')};
`;

export const NameBox = styled.div`
  ${({ theme }) => theme.mixins.flexBox('row', 'center', 'flex-start')}
  width: ${rem(120)};

  h2 {
    font-size: ${rem(16)};
  }
  div {
    font-size: ${rem(14)};
    margin-left: ${rem(10)};
  }
`;

export const ChatBox = styled.div`
  width: ${rem(60)};
  height: 100%;
  background-color: ${({ theme }) => theme.colors.black_005};
  ${({ theme }) => theme.mixins.flexBox('row', 'center', 'center')}
`;
