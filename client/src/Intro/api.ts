import { jsonInstance } from 'shared/utils/axios';

// 회원 조회
export const getUser = async () => {
  return await jsonInstance.get<any>('/members/me');
};

// 로그아웃
export const logout = async () => {
  return await jsonInstance.post<any>('/logout');
};

// 회원 탈퇴
export const deleteUser = async () => {
  return await jsonInstance.delete<any>('/members/me');
};