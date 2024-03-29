import React from 'react';
import { Login, PrivateRoute, Register } from './features/UserAuth';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Redirect,
} from 'react-router-dom';
import Dashboard from './features/Dashboard/Dashboard';
import { AppErrorBoundary } from './features/ErrorHandling';

const App = () => {
    return (
        <AppErrorBoundary>
            <Router>
                <Switch>
                    <Route path='/login' exact>
                        <Login />
                    </Route>
                    <Route path='/register' exact>
                        <Register />
                    </Route>
                    <PrivateRoute path='/dashboard' exact>
                        <Dashboard />
                    </PrivateRoute>
                    <Route path='/' exact>
                        <Redirect to='/dashboard' />
                    </Route>
                </Switch>
            </Router>
        </AppErrorBoundary>
    );
};

export default App;
