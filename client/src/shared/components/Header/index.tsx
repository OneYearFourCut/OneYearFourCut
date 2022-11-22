import * as B from './components/HeaderButtons';
import * as C from './components/HeaderBox';
import { ModalStore } from 'store/store';
import ModalBackdrop from '../Modal/components/ModalBackdrop';
import { Profile } from '../Modal/components/Profile';
const Header = () => {
  const { target } = ModalStore();

  return (
    <>
      {target.ProfileModal ? (
        <ModalBackdrop>
          <Profile></Profile>
        </ModalBackdrop>
      ) : (
        <C.HeaderBox>
          <B.HeaderBackbtn></B.HeaderBackbtn>
          <h2>올해 네 컷</h2>
          <B.HeaderBellbtn></B.HeaderBellbtn>
          <B.HeaderHamburgerbtn></B.HeaderHamburgerbtn>
        </C.HeaderBox>
      )}
    </>
  );
};

export default Header;
