import { createReducer }     from '../utils';
import { UPDATE_MENU, UPDATE_PLAN_VIEW,UPDATE_DISPLAY_VIEW } from 'constants/menu';

const initialState = {
	displayView : {
		id : 1,
		visible : false,
		iconName : 'display',
		className : 'fa fa-book fa-3x',
		displayLocationId : null,
		displayedData: null
	},
	healthView : {
		id : 2,
		visible : false,
		iconName : 'health',
		className : 'fa fa-medkit fa-3x',
		displayLocationId : null
	},
	planView : {
		id : 3,
		visible : false,
		iconName : 'plan',
		className : 'fa fa-plane fa-3x',
		displayLocationId : null,
		statusView : 'viewPlans',
		planId : null
	},
	settingView : {
		id : 4,
		visible : false,
		iconName : 'setting',
		className :'fa fa-cogs fa-3x',
		displayLocationId : null
	}
};
export default createReducer(initialState, {
  [UPDATE_MENU] : (state,action) => {
  	let tempObj  = Object.assign({},state);
  	for(let item in tempObj) {
  		if(tempObj.hasOwnProperty(item) && tempObj[item].iconName === action.displayName){
  			tempObj[item].visible = !tempObj[item].visible;
  			if(action.locationId){
  				tempObj[item].displayLocationId = action.locationId;
  			}
  		}else{
  			tempObj[item].visible = false;
  		}
  	}
  	return tempObj
  },
  [UPDATE_DISPLAY_VIEW] : (state, action) => {
  	let tempObj  = Object.assign({},state);
  	for(let item in tempObj) {
  		tempObj[item].visible = false;
  	}
  	tempObj.displayView.visible = true;
  	tempObj.displayView.displayLocationId = action.locationId;
  	tempObj.displayView.displayedData = action.displayedData;
  	return tempObj
  },
  [UPDATE_PLAN_VIEW] : (state, action) => {
  	let tempObj = Object.assign({}, state);
  	tempObj.planView.planId = action.planId
  	tempObj.planView.statusView = action.status;
  	return tempObj;
  }
});