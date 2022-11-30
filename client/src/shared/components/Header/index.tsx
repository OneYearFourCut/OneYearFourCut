import * as B from './components/HeaderButtons';
import * as C from './components/HeaderBox';
import { ModalStore } from 'store/store';
import { Profile } from '../Modal/Profile';
import { useNavigateSearch } from 'shared/hooks/useNavigateSearch';
import ModalBackdrop from '../Modal/components/ModalBackdrop';

const Header = () => {
  const { target } = ModalStore();
  const navigateSearch = useNavigateSearch();
  if (window.location.pathname === '/') return null;
  return (
    <>
      {target.ProfileModal ? (
        <ModalBackdrop>
          <Profile />
        </ModalBackdrop>
      ) : (
        <C.HeaderBox>
          <B.HeaderBackbtn />
          <h2 onClick={() => navigateSearch('/', {})}>올해 네 컷</h2>
          <B.HeaderBellbtn />
          <B.HeaderHamburgerbtn />
        </C.HeaderBox>
      )}
    </>
  );
};

export default Header;
