const INIT_URL = 'initUrl';

export const getinitUrl = (): string => {
  const initUrl = sessionStorage.getItem(INIT_URL);
  return initUrl ? JSON.parse(initUrl) : null;
};

export const setinitUrl = (url: string): void => {
  sessionStorage.setItem(INIT_URL, JSON.stringify(url));
};

export const clearinitUrl = (): void => {
  sessionStorage.removeItem(INIT_URL);
};
