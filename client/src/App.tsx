import React from 'react';
import Header from 'shared/components/Header';
import UploadPicture from 'UploadPicture';
import AlarmList from 'AlarmList';
import Toast from 'shared/components/Toast';
// import { ToastRenderBox } from './shared/components/Toast/components/ToastContainer';
function App() {
  return (
    <>
      <Header></Header>
      <AlarmList></AlarmList>
      <UploadPicture></UploadPicture>
      <Toast></Toast>
    
    </>
  );
}

export default App;
