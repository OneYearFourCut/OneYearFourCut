import { rem } from 'polished';

const filter = () => {
  return (
    <svg
      width={24}
      height={24}
      viewBox='0 0 24 24'
      fill='none'
      xmlns='http://www.w3.org/2000/svg'
      style={{ width: 24, height: 24, position: 'relative' }}
      preserveAspectRatio='none'
    >
      <path
        d='M6 3V21M6 3L10 7M6 3L2 7M18 21V3M18 21L22 17M18 21L14 17'
        stroke='#316232'
        strokeWidth={2}
        strokeLinecap='round'
        strokeLinejoin='round'
      />
    </svg>
  );
};

export default filter;
