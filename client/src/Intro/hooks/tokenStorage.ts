const TOKEN = 'token';

interface TokenType {
  access_token?: string;
  refresh_token?: string;
}

// 로컬 스토리지 읽기
export const getStoredToken = (): TokenType | null => {
  const storedToken = localStorage.getItem(TOKEN);
  return storedToken !== null ? JSON.parse(storedToken) : null;
};

// 로컬 스토리지에 추가
export const setStoredToken = (value: string): void => {
  localStorage.setItem(TOKEN, value);
};

// 로컬 스토리지에서 제거
export const clearStoredToken = (): void => {
  localStorage.removeItem(TOKEN);
};
