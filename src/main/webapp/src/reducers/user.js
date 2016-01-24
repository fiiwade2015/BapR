import { createReducer } 	from '../utils';
import { LOGIN_SUCCESS } 	from 'constants/user';
import { LOGIN_FAILED }	 	from 'constants/user';
import { ADD_PLAN }	 	 	from 'constants/user';
import { REMOVE_LOCATION }	from 'constants/user';
import {START_JOURNEY}		from 'constants/user';
import { USER_LOCATION }		from 'constants/user';

const initialState = {
	user: {
		id : null,
		name : '',
		journeys : [],
		currentJourneyId : null,
		currentLocation: {
			lat : null,
			long: null
		},
		currentBuildingJourneyId : null,
		health: {}
	},
	errorMessage: ''
};

export default createReducer(initialState, {
	[LOGIN_SUCCESS] : (state, action) => {
		let temp = Object.assign({}, state);
		temp.user.id = action.user.id;
		temp.user.name = action.user.name;
		temp.user.userAuth = action.user.status;
		temp.user.loginStatus = true;
		temp.errorMessage = '';
		return temp;
	},
	[LOGIN_FAILED] : (state, action) => {

		let temp = Object.assign({}, state);
		temp.user.id = null;
		temp.user.name = '';
		temp.user.userAuth = 'guest';
		temp.user.loginStatus = false;
		temp.errorMessage = action.errorMessage;
		return temp;
	},
	[ADD_PLAN]	: (state, action) => {
		let temp = Object.assign({},state);
		temp.user.journeys.push(action);
		return temp;
	},
	[START_JOURNEY]: (state,action) => {
		let temp = Object.assign({},state);
		temp.user.plans.map(plan => {
			if(plan.id === action){
				plan.status = 'inProgress';
			}
		});
		return temp;
	},
	
	[REMOVE_LOCATION] : (state,action) => {
		let temp =  Object.assign({},state);
		for(let i = 0; i < temp.plans.length; i++) {
			if(temp.plans[i].id === action.planId) {
				for(let j = 0; j < temp.plans[i].locations.length; j++) {
					if(temp.plans[i].locations[j].id === action.locationId){
						temp.plans[i].locations = temp.plans[i].locations.splice(j,1);
						break;
					}
				}
			}
		}
		return temp;
	},
	[USER_LOCATION] : (state, action) => {
		let temp =  Object.assign({},state);
		temp.user.currentLocation.lat = action.lat;
		temp.user.currentLocation.long = action.long;
		return temp;
	}
});
