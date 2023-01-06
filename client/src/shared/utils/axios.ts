import axios, { AxiosError, AxiosRequestConfig } from 'axios';
import { setStoredToken, getStoredToken } from 'Intro/hooks/tokenStorage';

const APPLICATION_JSON = 'application/json';
const MULTIPART_FORM_DATA = 'multipart/form-data';

//json용도
const jsonInstance = axios.create({
  baseURL: process.env.REACT_APP_SERVER_URL,
  timeout: 5000,
  headers: {
    'Content-Type': APPLICATION_JSON,
    Authorization: getStoredToken()?.access_token,
  },
});

//form-data용도
const formdataInstance = axios.create({
  baseURL: process.env.REACT_APP_SERVER_URL,
  timeout: 5000,
  headers: {
    'Content-Type': MULTIPART_FORM_DATA,
    Authorization: getStoredToken()?.access_token,
  },
});

const refreshInstance = axios.create({
  baseURL: process.env.REACT_APP_SERVER_URL,
  timeout: 5000,
  headers: {
    'Content-Type': APPLICATION_JSON,
  },
});

let lock = false;
let requestQueue: ((access_token: string) => void)[] = [];

function handleQueue(callback: (access_token: string) => void) {
  requestQueue.push(callback);
}

function refreshTrigger(access_token: string) {
  requestQueue.forEach((callback) => callback(access_token));
}

jsonInstance.interceptors.request.use((config: any) => {
  config.headers.Authorization = getStoredToken()?.access_token;
  return config;
});

formdataInstance.interceptors.request.use((config: any) => {
  config.headers.Authorization = getStoredToken()?.access_token;
  return config;
});

refreshInstance.interceptors.request.use((config: any) => {
  if (lock) {
    return new Promise((resolve) => {
      handleQueue((token: string) => {
        config.url = '/auth/refresh/check';
        resolve(config);
      });
    });
  } else {
    lock = true;
    config.headers.Authorization = getStoredToken()?.access_token;
    config.headers.refresh = getStoredToken()?.refresh_token;
    return config;
  }
});

const ErrorHandler457 = (err: any) => {
  alert('로그인을 다시 해주세요');
  window.location.href = '/';
  return Promise.reject(err);
};

const originalRequestReFetch = (
  originalRequest: AxiosRequestConfig<any>,
  token: string | undefined,
  type: string,
) => {
  return axios({
    method: originalRequest.method,
    url: originalRequest.baseURL + originalRequest.url!,
    data: originalRequest.data,
    headers: {
      'Content-Type': type,
      Authorization: token,
    },
  });
};

const responseInterceptorHandle = async (err: AxiosError, type: string) => {
  const {
    config, //original request
    response,
  } = err;
  const status = response?.status;
  const originalRequest = config;
  if (status === 457) {
    return ErrorHandler457(err);
  } else if (status === 456) {
    if (lock) {
      return new Promise((resolve) => {
        handleQueue((token: string) => {
          resolve(originalRequestReFetch(originalRequest!, token, type));
        });
      });
    }
    lock = true;
    try {
      const { headers } = await axios.get(
        `${process.env.REACT_APP_SERVER_URL}/auth/refresh`,
        {
          headers: {
            Authorization: getStoredToken()?.access_token,
            refresh: getStoredToken()?.refresh_token,
          },
        },
      );

      await storeToken(headers.authorization, headers.refresh);

      const access_token = headers.authorization;
      const result = await originalRequestReFetch(
        originalRequest!,
        access_token,
        type,
      );
      refreshTrigger(access_token!);

      requestQueue = [];
      lock = false;

      return result;
    } catch (err: any) {
      if (err.response.status === 457) return ErrorHandler457(err);
    }
  } else {
    return Promise.reject(err);
  }
};

jsonInstance.interceptors.response.use(
  (res) => res,
  async (err) => responseInterceptorHandle(err, APPLICATION_JSON),
);

formdataInstance.interceptors.response.use(
  (res) => res,
  async (err) => responseInterceptorHandle(err, MULTIPART_FORM_DATA),
);

refreshInstance.interceptors.response.use(
  async (res) => {
    if (res.headers.authorization && res.headers.refresh)
      await storeToken(res.headers.authorization, res.headers.refresh);
    refreshTrigger(res.headers.authorization!);
    lock = false;
    requestQueue = [];
    return res;
  },
  async (err) => {
    if (err.response.status === 457) return ErrorHandler457(err);
    else return err;

  },
);

const storeToken = async (authorization?: string, refresh?: string) => {
  const access_token = authorization;
  const refresh_token = refresh;
  await setStoredToken(
    JSON.stringify({
      access_token: access_token,
      refresh_token: refresh_token,
    }),
  );
};
export { jsonInstance, formdataInstance, refreshInstance };
