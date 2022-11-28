import { DefaultTheme } from 'styled-components';
import { rem } from 'polished';

const colors = {
  black_001: '#121111',
  black_002: '#1D1D1D',
  black_003: '#8C8C8C',
  black_004: '#A6A6A6',
  black_005: '#CBCBCB',
  black_006: '#E5E5E5',
  black_007: '#FCFCFC',
  black_008: '#F4F4F4',
  black_009: '#AFAFAF',

  beige_001: '#FFF6CA',
  beige_002: '#FDFAEB',
  beige_003: '#FFFDF5',
  beige_004: '#FFFEFB',
  beige_005: '#FBF6F2',

  gold_001: '#A76D28',
  gold_002: '#D99441',
  gold_003: '#E3A457',
  gold_004: '#E0B580',

  gray_001: '#09090C',
  gray_002: '#282B34',
  gray_003: '#505563',
  gray_004: '#92949C',

  red_001: '#B90211',
  red_002: '#F21628',
  red_003: '#F54553',
  red_004: '#FCCED1',
  red_005: '#FCC7CC',
  red_006: '#3B0105',
  red_007: '#A1020F',

  green_001: '#153515',
  green_002: '#316232',
  green_003: '#518252',
  green_004: '#8BB88C',
  green_005: '#B1EEB2',
  green_006: '#278F59',
  green_007: '#96CB97',

  kakao_001: '#FEE500',
  kakao_002: '#000000',
};

const mixins = {
  // flex
  flexBox: (
    direction = 'row',
    align = 'center',
    justify = 'center',
    wrap = 'nowrap',
  ) => `
        display: flex;
        flex-direction: ${direction};
        align-items: ${align};
        justify-content: ${justify};
        flex-wrap: ${wrap};
      `,
};

const flex = {
  center: 'display: flex; align-items: center; justify-content: center;',
};
export type ColorsTypes = typeof colors;

export const theme: DefaultTheme = {
  colors,
  mixins,
  flex,
};
