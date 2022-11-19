import * as B from './components/HeaderButtons';
import * as C from './components/HeaderBox';
const Header = () => {
  return (
    <C.HeaderBox>
      <B.HeaderBackbtn></B.HeaderBackbtn>
      <h2>올해 네 컷</h2>
      <B.HeaderBellbtn></B.HeaderBellbtn>
      <B.HeaderHamburgerbtn></B.HeaderHamburgerbtn>
    </C.HeaderBox>
  );
};

export default Header;
