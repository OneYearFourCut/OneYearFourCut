import React from 'react';
import Header from 'shared/components/Header';
import UploadPicture from 'UploadPicture';
import AlarmList from 'AlarmList';
import ToastRender from 'shared/components/Toast';
import { createBrowserRouter, RouterProvider, Outlet } from 'react-router-dom';
import ForBidden from 'ForBidden';
import Intro from 'Intro/Intro';
import GallerySetting from 'GallerySetting/GallerySetting';
import GalleryAllPic from 'Gallery/GalleryAllPic';
import GalleryFourPic from 'Gallery/GalleryFourPic';
import RedirectPage from 'Intro/RedirectPage';

const router = createBrowserRouter([
  {
    path: '/',
    element: (
      <>
        <Header />
        <ToastRender />
        <Outlet />
      </>
    ),
    errorElement: <ForBidden />,
    children: [
      {
        index: true,
        element: <Intro />,
      },
      { path: '/gallerySetting', element: <GallerySetting /> },
      { path: '/alarmList', element: <AlarmList /> },
      { path: '/uploadPicture', element: <UploadPicture /> },
      { path: '/localStorage', element: <RedirectPage /> },
      { path: '/allPic', element: <GalleryAllPic /> },
      { path: '/fourPic', element: <GalleryFourPic /> },
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
