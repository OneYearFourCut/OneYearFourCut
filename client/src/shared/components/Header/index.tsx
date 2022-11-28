import * as B from './components/HeaderButtons';
import * as C from './components/HeaderBox';
import { ModalStore } from 'store/store';
import ModalBackdrop from '../Modal/components/ModalBackdrop';
import { Profile } from '../Modal/components/Profile';
const Header = () => {
  const { target } = ModalStore();
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
          <h2>올해 네 컷</h2>
          <B.HeaderBellbtn />
          <B.HeaderHamburgerbtn />
        </C.HeaderBox>
      )}
    </>
  );
};

export default Header;
