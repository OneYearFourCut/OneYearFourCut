import React, { Suspense } from 'react';
import UploadPicture from 'UploadPicture';
import AlarmList from 'AlarmList';
import ChatList from 'ChatList';
import ToastRender from 'shared/components/Toast';
import { createBrowserRouter, RouterProvider, Outlet } from 'react-router-dom';
import ForBidden from 'ForBidden';
import Intro from 'Intro/Intro';
import GallerySetting from 'GallerySetting/GallerySetting';
import RedirectPage from 'Intro/RedirectPage';
import SinglePicPage from './SinglePicture/index';
import OnePicPage from 'SinglePicture/OnePage/OnePicPage';
import AuthCheck from 'shared/hooks/useAuth';
import { TriggerBox } from 'AlarmList/components/AlarmContainer';
import SingleComment from './SingleComments/index';

const Header = React.lazy(() => import('shared/components/Header'));
const GalleryFourPic = React.lazy(() => import('Gallery/GalleryFourPic'));
const GalleryAllPic = React.lazy(() => import('Gallery/GalleryAllPic'));
const AllComment = React.lazy(() => import('./AllComments/AllComments'));
const ChatRoom = React.lazy(() => import('./Chatroom'));

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
      {
        path: '/chatroom/:roomId',
        element: (
          <Suspense fallback={<TriggerBox />}>
            <AuthCheck>
              <ChatRoom />
            </AuthCheck>
          </Suspense>
        ),
      },
      {
        path: '/gallerySetting',
        element: (
          <AuthCheck>
            <GallerySetting />
          </AuthCheck>
        ),
      },
      {
        path: '/alarmList',
        element: (
          <AuthCheck>
            <AlarmList />
          </AuthCheck>
        ),
      },
      {
        path: '/uploadPicture/:galleryId',
        element: (
          <AuthCheck>
            <UploadPicture />
          </AuthCheck>
        ),
      },
      { path: '/localStorage', element: <RedirectPage /> },

      {
        path: '/allPic/:galleryId',
        element: (
          <Suspense fallback={<TriggerBox />}>
            <GalleryAllPic />
          </Suspense>
        ),
      },
      {
        path: `/fourPic/*`,
        element: (
          <Suspense fallback={<TriggerBox />}>
            <GalleryFourPic />
          </Suspense>
        ),
      },
      { path: `/allPic/:galleryId/artworks`, element: <SinglePicPage /> },
      {
        path: '/allPic/:galleryId/artworks/comments',
        element: (
          <Suspense fallback={<TriggerBox />}>
            <AllComment />,
          </Suspense>
        ),
      },
      {
        path: '/allPic/:galleryId/:artworkId',
        element: (
          <Suspense fallback={<div>Loading...</div>}>
            <OnePicPage />
          </Suspense>
        ),
      },
      {
        path: '/allPic/:galleryId/:artworkId/comments',
        element: (
          <Suspense fallback={<div>Loading...</div>}>
            <SingleComment single={true} />
          </Suspense>
        ),
      },
      {
        path: '/allPic/:galleryId/:artworkId/comments/:commentId',
        element: (
          <Suspense fallback={<div>Loading...</div>}>
            <SingleComment single={false} />
          </Suspense>
        ),
      },
      {
        path: '/chatList',
        element: (
          <AuthCheck>
            <ChatList />
          </AuthCheck>
        ),
      },
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
