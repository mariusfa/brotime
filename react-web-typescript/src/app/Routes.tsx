import React from 'react';
import { Switch, Route} from 'react-router-dom';
import HomePage from './HomePage';
import OverviewPage from './OverviewPage';

const Routes = () => {
  return (
      <Switch>
        <Route path="/overview" component={OverviewPage}/>
        <Route path="/" component={HomePage}/>
      </Switch>
  )
}

export default Routes;