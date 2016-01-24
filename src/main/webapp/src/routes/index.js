import React                 from 'react';
import { Route, IndexRoute } from 'react-router';
import CoreLayout            from 'layouts/CoreLayout';
import {requireAuthentication} from 'components/AuthComponent';
import HomeView              from 'views/HomeView';
import AboutView             from 'views/AboutView';
import LoginView			 from 'views/LoginView';

export default (
  <Route        component={CoreLayout} path='/'>
    <IndexRoute component={LoginView} />
    <Route 		component={requireAuthentication(HomeView)} path="/homeView" />
    <Route      component={AboutView}  path='/about' />
  </Route>
);
