import { jsonInstance } from 'shared/utils/axios';


// 로그아웃
export const logout = async () => {
  return await jsonInstance.post<any>('/logout');
}

// 회원 조회
export const getUser = async () => {
  return await jsonInstance.get<any>('/members/me');
}