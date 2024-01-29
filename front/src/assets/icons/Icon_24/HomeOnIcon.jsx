import styled from 'styled-components';
import theme from '../../../styles/theme';

const Home = styled.svg`
    width: 24px;
    height: 24px;
    fill: ${(props) => (props.active ? theme.color.grayScale500 : theme.color.grayScale300)};
    stroke:${(props) => (props.active ? theme.color.grayScale500 : theme.color.grayScale300)};
    strokeWidth:2;
    viewBox: 0 0 24 24;
    xmlns: http://www.w3.org/2000/svg;
  `;

function HomeOnIcon() {
  return (
    <Home>
      <g>
        <path d="M4.22101 7.8174L11.221 3.61739C11.8544 3.23738 12.6456 3.23737 13.279 3.61739L20.279 7.81739C20.8814 8.17884 21.25 8.82985 21.25 9.53238V19C21.25 20.1046 20.3546 21 19.25 21H5.25C4.14543 21 3.25 20.1046 3.25 19V9.53238C3.25 8.82985 3.6186 8.17884 4.22101 7.8174Z" />
      </g>
    </Home>
  );
}

export default HomeOnIcon;
