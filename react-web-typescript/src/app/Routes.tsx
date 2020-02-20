import React from 'react';
import { Switch, Route } from 'react-router-dom';
import HomePage from './HomePage';
import OverviewPage from './OverviewPage';
import RegisterPage from './RegisterPage';
import LoginPage from './LoginPage';

const Routes = () => {
  return (
      <Switch>
        <Route path="/login" component={LoginPage} />
        <Route path="/register" component={RegisterPage} />
        <Route path="/overview" component={OverviewPage}/>
        <Route path="/" component={HomePage}/>
      </Switch>
  )
}

export default Routes;