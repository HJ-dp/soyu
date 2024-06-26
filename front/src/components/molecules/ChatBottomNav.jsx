import styled from 'styled-components';
import { useState } from 'react';

import add from '../../assets/icons/material_24/add.svg';
import send from '../../assets/icons/material_24/send.svg';
import color from '../../styles/color';

function ChatBottomNav({ sendHandler }) {
  const [input, setInput] = useState('');

  return (
    <SForm action="#">
      <img src={add} alt="추가" />
      <SInput
        type="text"
        onChange={(e) => setInput(e.target.value)}
        onKeyDown={(e) => {
          if (e.key === 'Enter') {
            sendHandler(e, input);
            setInput('');
          }
        }}
        value={input}
      />
      <button
        type="button"
        onClick={(e) => {
          sendHandler(e, input);
          setInput('');
        }}
      >
        <img src={send} alt="전송" />
      </button>
    </SForm>
  );
}

export default ChatBottomNav;

const SForm = styled.form`
  display: flex;
  align-items: center;
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 8px 16px;
  gap: 10px;
  /* background-color: ${color.primaryColor}; */
  background-color: white;
`;

const SInput = styled.input`
  width: inherit;
  border-radius: 10px;
  border: 1px solid ${color.grayScale300};
  height: 30px;
  padding: 0 5px;
`;
