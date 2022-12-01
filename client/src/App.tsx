import React, { Suspense } from 'react';
import Header from 'shared/components/Header';
import UploadPicture from 'UploadPicture';
import AlarmList from 'AlarmList';
import ToastRender from 'shared/components/Toast';
import { createBrowserRouter, RouterProvider, Outlet } from 'react-router-dom';
import ForBidden from 'ForBidden';
import Intro from 'Intro/Intro';
import GallerySetting from 'GallerySetting/GallerySetting';
import RedirectPage from 'Intro/RedirectPage';
import SinglePicPage from './SinglePicture/index';
import SingleComment from './SingleComments/index';
const GalleryFourPic = React.lazy(() => import('Gallery/GalleryFourPic'));
const GalleryAllPic = React.lazy(() => import('Gallery/GalleryAllPic'));

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
      {
        path: '/allPic/:galleryId',
        element: (
          <Suspense fallback={<div>Loading...</div>}>
            <GalleryAllPic />
          </Suspense>
        ),
      },
      {
        path: `/fourPic/:galleryId`,
        element: (
          <Suspense fallback={<div>Loading...</div>}>
            <GalleryFourPic />
          </Suspense>
        ),
      },
      { path: '/SinglePic', element: <SinglePicPage /> },
      { path: '/testing', element: <SingleComment /> },
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
