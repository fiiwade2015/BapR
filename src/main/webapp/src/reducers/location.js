import { createReducer } 	from '../utils';
import { LOGIN_SUCCESS } 	from 'constants/user';
import { LOGIN_FAILED }	 	from 'constants/user';
import { ADD_PLAN }	 	 	from 'constants/user';
import { EDIT_PLAN}			from 'constants/user';
import { ADD_LOCATION }	 	from 'constants/user';
import { REMOVE_LOCATION }	from 'constants/user';
import {START_JOURNEY}		from 'constants/user';

const initialState = {
	id: null,
	geoData: {
		lat: null,
		long: null
	},
	name: '',
	type: '',

	//De adaugat si restul

	flags:{
		visited: false,
		partOfJourney: false
	}
}


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
		temp.user.plans.push(action);
		return temp;
	},
	[EDIT_PLAN] : (state, action) => {
		let temp = Object.assign({},state);
		temp.editPlans.planId = action.planId;
		temp.editPlans.status = action.status;
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
	[ADD_LOCATION] : (state, action) => {
		let temp =  Object.assign({},state);
		for(let i = 0; i < temp.user.plans.length; i++) {
			if(temp.user.plans[i].id === action.planId) {
				temp.user.plans[i].locations.push(action.location);
			}
		}
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
	}
});