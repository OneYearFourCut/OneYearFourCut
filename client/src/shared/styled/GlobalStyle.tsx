import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: 'Noto Sans', 'Noto Sans KR', sans-serif;
}
body{
    padding-top: 5vh;
}
button{
    cursor:pointer;
}

`;

export default GlobalStyle;
