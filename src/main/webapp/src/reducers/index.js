import { combineReducers }    from 'redux';
import { routeReducer }       from 'redux-simple-router';
import counter                from './counter';
import user                   from './user';
import menu					  from './menu';
export default combineReducers({
  user,
  counter,
  menu,
  routing: routeReducer
});
