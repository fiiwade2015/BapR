import { createReducer }     from '../utils';
import { UPDATE_TOKEN, UPDATE_USER_STATUS ,EDIT_PLAN} from 'constants/application';

const initialState = {
	AuthToken: null,
	isLogedIn: null,
	maxLocNr : 1000,
	editJourny: {
		planId : null,
		status : 'stop'
	}
};
export default createReducer(initialState, {
  [UPDATE_TOKEN] : (state,action) => {
  	let tempObj  = Object.assign({},state);
  	tempObj.AuthToken = state.AuthToken;
  	return tempObj
  },
  [UPDATE_USER_STATUS] : (state,action) => {
  	let tempObj  = Object.assign({},state);
  	tempObj.AuthToken = state.isLogedIn;
  	return tempObj
  },
  [EDIT_PLAN] : (state, action) => {
		let temp = Object.assign({},state);
		temp.editPlans.planId = action.planId;
		temp.editPlans.status = action.status;
		return temp;
	}
});