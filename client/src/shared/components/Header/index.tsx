import * as B from './components/HeaderButtons';
import * as C from './components/HeaderBox';
import { ModalStore } from 'store/store';
import { Profile } from '../Modal/Profile';
import { useNavigateSearch } from 'shared/hooks/useNavigateSearch';
import ModalBackdrop from '../Modal/components/ModalBackdrop';
import { Suspense } from 'react';
import { loginStore } from 'store/store';
const Header = () => {
  const { target } = ModalStore();
  const { user } = loginStore();
  const navigateSearch = useNavigateSearch();

  // console.log(window.location.pathname);
  if (
    window.location.pathname === '/' ||
    window.location.pathname === '/localStorage'
  )
    return null;
  
  const handleHeaderTitle = () => {
    let url = user?.galleryId ? `/fourPic/${user.galleryId}` : '';
    navigateSearch(url, {});
  }
    

  return (
    <Suspense fallback={<></>}>
      {target.ProfileModal ? (
        <ModalBackdrop>
          <Profile />
        </ModalBackdrop>
      ) : (
        <C.HeaderBox>
          <B.HeaderBackbtn />
          <h2 onClick={handleHeaderTitle}>올해 네 컷</h2>
          <B.HeaderBellbtn />
          <B.HeaderHamburgerbtn />
        </C.HeaderBox>
      )}
    </Suspense>
  );
};

export default Header;
