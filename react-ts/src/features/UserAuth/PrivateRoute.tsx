import React, { useEffect, useState } from 'react';
import { Route, useHistory } from 'react-router-dom';

interface Props {
    children: React.ReactChildren;
}

const PrivateRoute = ({ children, ...rest }: Props) => {
    const [isAuth, setIsAuth] = useState(false);
    const history = useHistory();

    useEffect(() => {
        if (localStorage.getItem('token')) {
            setIsAuth(true);
        } else {
            history.replace('/login');
        }
    }, []);

    return <Route {...rest}>{children}</Route>;
};

export default PrivateRoute;
