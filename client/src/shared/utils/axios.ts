import axios from 'axios';
import { getStoredToken } from 'Intro/hooks/tokenStorage';

let ACCESS_TOKEN = getStoredToken()?.access_token;

//json용도
const jsonInstance = axios.create({
  baseURL: process.env.REACT_APP_SERVER_URL,
  timeout: 2000,
  headers: {
    'Content-Type': 'application/json',
    Authorization: ACCESS_TOKEN,
  },
});

//form-data용도
const formdataInstance = axios.create({
  baseURL: process.env.REACT_APP_SERVER_URL,
  timeout: 5000,
  headers: {
    'Content-Type': 'multipart/form-data',
    Authorization: ACCESS_TOKEN,
  },
});

jsonInstance.interceptors.request.use((config :any)=> {

  config.headers.Authorization = getStoredToken()?.access_token;
  return config;
});

formdataInstance.interceptors.request.use((config :any)=> {

  config.headers.Authorization = getStoredToken()?.access_token;
  return config;
});

export { jsonInstance, formdataInstance };
