import axios from 'axios';

//json용도
const jsonInstance = axios.create({
  baseURL: '',
  timeout: 1000,
  headers: {
    'Access-Control-Allow-Origin': '*',
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

//form-data용도
const formdataInstance = axios.create({
  baseURL: '',
  timeout: 1000,
  headers: {
    'Content-Type': 'multipart/form-data',
  },
  withCredentials: true,
});

export { jsonInstance, formdataInstance };
