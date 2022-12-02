import { jsonInstance } from 'shared/utils/axios';
import { loginStore } from 'store/store';

// 회원 조회
export const getUser = async () => {
  return await jsonInstance.get<any>('/members/me');
};
// 회원 조회 후 데이터 저장
export const saveUser = async () => {
  const { setUser, setIsLoggedIn } = loginStore();
  return await jsonInstance.get<any>('/members/me').then((res) => {
    setIsLoggedIn();
    setUser(res.data);
    loginStore.subscribe((state) => console.log('바뀌었어요!: ', state));
    return res.data;
  });
};

// 로그아웃
export const logout = async () => {
  return await jsonInstance.get<any>('/auth/logout');
};

// 회원 탈퇴
export const deleteUser = async () => {
  return await jsonInstance.delete<any>('/members/me');
};
