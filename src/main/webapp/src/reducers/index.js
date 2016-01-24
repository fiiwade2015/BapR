import { combineReducers }    from 'redux';
import { routeReducer }       from 'redux-simple-router';
import counter                from './counter';
import user                   from './user';
import menu					  from './menu';
import map					  from './map';
import journeys				  from './journey'
export default combineReducers({
  user,
  counter,
  menu,
  map,
  journeys,
  routing: routeReducer
});
