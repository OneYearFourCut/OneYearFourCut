import axios from 'axios';
import { getStoredToken } from 'Intro/hooks/tokenStorage';

let ACCESS_TOKEN = getStoredToken()?.access_token;

//json용도
const jsonInstance = axios.create({
  baseURL: process.env.REACT_APP_SERVER_URL,
  timeout: 1000,
  headers: {
    'Content-Type': 'application/json',
    authorization: ACCESS_TOKEN,
  },
});

//form-data용도
const formdataInstance = axios.create({
  baseURL: process.env.REACT_APP_SERVER_URL,
  timeout: 1000,
  headers: {
    'Content-Type': 'multipart/form-data',
    authorization: ACCESS_TOKEN,
  },
});

export { jsonInstance, formdataInstance };
