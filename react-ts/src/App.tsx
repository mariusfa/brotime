import React from 'react';
import { Login, Register } from './features/UserAuth';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Dashboard from './features/Dashboard/Dashboard';

const App = () => {
    return (
        <Router>
            <Switch>
                <Route path='/login' exact>
                    <Login />
                </Route>
                <Route path='/register' exact>
                    <Register />
                </Route>
                <Route path='/dashboard' exact>
                    <Dashboard />
                </Route>
            </Switch>
        </Router>
    );
}

export default App
