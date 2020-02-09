import React from 'react';
import styled from '@emotion/styled';

const NavBar = styled.div`
  text-align: center;
`;

const Nav = () => {
  return (
    <NavBar>
      <span>Brotime</span>
      <span>Settings</span>
    </NavBar>
  )
}

export default Nav;