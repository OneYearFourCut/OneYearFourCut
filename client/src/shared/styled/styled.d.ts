import 'styled-components';
import { ColorsTypes } from './Theme';

declare module 'styled-componets' {
    export interface DefaultTheme {
        colors: ColorsTypes;
    }
}