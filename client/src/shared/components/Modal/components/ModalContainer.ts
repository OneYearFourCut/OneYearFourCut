import styled from "styled-components";
import {rem} from 'polished';

//모달 배경(투명한 검은색)
const ModalBackdrop = styled.div`
    width: ${rem(428)};
    height: 100vh;
    background-color: rgba(0, 0, 0, 0.4);
    position: fixed;
    top:0;
    z-index: 99;
    ${({theme}) => theme.flex.center}
`
//모달창안에 있는 버튼이 들어있는 박스
const ModalViewBox = styled.div`

    ${({theme}) => theme.flex.center}
    flex-direction: column;
    width: ${rem(327)};
    height: ${rem(122)};
    background-color: white;
    border-radius: ${rem(10)};
    z-index: 100;
    position: relative;
    .ApplySVG{
        position: absolute;
        top: -20%;
        left: 40%;
        path{
            fill: ${({theme,color})=>color === "green" ? theme.colors.green_002 : theme.colors.red_002 };
        }
    }
    .Progressbtn{
        background-color: ${({theme,color})=>color === "green" ? theme.colors.green_005 : theme.colors.red_005 };
        color: ${({theme,color})=>color === "green" ? theme.colors.green_002 : theme.colors.red_002 };
    }
`

const ModalbtnBox = styled.div`
    ${({theme}) => theme.flex.center}
    width: ${rem(259)};
    height: ${rem(34)};
    margin-top: ${rem(10)};
    button{
        width: ${rem(113)};
        height: ${rem(34)};
        border-radius: ${rem(5)};
        border: none;
        margin: ${rem(7)};
        font-weight: bold;
        background-color: white;
    }

`
export { ModalBackdrop, ModalViewBox, ModalbtnBox };