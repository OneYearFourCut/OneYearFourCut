import React from 'react';
import Header from 'shared/components/Header';
import UploadPicture from 'UploadPicture';
import AlarmList from 'AlarmList';
import ToastRender from 'shared/components/Toast';
import { createBrowserRouter, RouterProvider, Outlet } from 'react-router-dom';
import ForBidden from 'ForBidden';
const router = createBrowserRouter([
  {
    path: '/',
    element: (
      <>
        <Header />
        <ToastRender />
        <Outlet/>
      </>
    ),
    errorElement: <ForBidden />,
    children: [
      {
        index: true,
        element: <div>여기에 메인페이지 넣으시면 됩니다.</div>,
      },
      { path: '/alarmList', element: <AlarmList /> },
      { path: '/uploadPicture', element: <UploadPicture /> },
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
