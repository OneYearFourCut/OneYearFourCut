import Snowfall from 'react-snowfall';
import Scroll from './components/Scroll';

const Intro = () => {
  return (
    <>
      <Snowfall
        color='aliceblue'
        snowflakeCount={100}
        style={{
          position: 'fixed',
          width: '100vw',
          height: '100vh',
        }}
      />
      <Scroll />
    </>
  );
  // <Envelope />;
};

export default Intro;
