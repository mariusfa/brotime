import React, { useEffect, useState } from 'react';
import { Route, useHistory } from 'react-router-dom';

interface Props {
    path: string,
    exact: any
}

const PrivateRoute: React.FC<Props> = ({ children, ...rest }) => {
    const history = useHistory();
    const token = localStorage.getItem('token');
     
    if (!token) {
        history.push('/login');
    }

    return <Route {...rest}>{children}</Route>;
};

export default PrivateRoute;
