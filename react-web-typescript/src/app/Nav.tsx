import React from 'react';
import styled from '@emotion/styled';
import { useHistory } from 'react-router';

const NavBar = styled.div`
  text-align: center;
`;

const Nav = () => {
    const history = useHistory();

    const handleOverviewClick = () => {
        history.push('/overview')
    }

    return (
        <NavBar>
            <span>Brotime</span>
            <button onClick={handleOverviewClick}>Overview</button>
        </NavBar>
    )
}

export default Nav;