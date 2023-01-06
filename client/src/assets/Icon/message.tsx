import { rem } from 'polished';

export default function message() {
  return (
    <>
      <svg
        xmlns='http://www.w3.org/2000/svg'
        width={rem(32)}
        height={rem(32)}
        preserveAspectRatio='xMidYMid meet'
        viewBox='0 0 24 24'
      >
        <path
          fill='#8c8c8c'
          d='M20 2H4a2 2 0 0 0-2 2v18l4-4h14a2 2 0 0 0 2-2V4a2 2 0 0 0-2-2Z'
        />
      </svg>
    </>
  );
}
