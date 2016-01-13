import { LOGIN_SUCCESS } 	from 'constants/user';
import { LOGIN_FAILED}	 	from 'constants/user';
import { ADD_PLAN }	 	 	from 'constants/user';
import { ADD_LOCATION }	 	from 'constants/user';
import { REMOVE_LOCATION }	from 'constants/user';
import {EDIT_PLAN}			from 'constants/user';
import {START_JOURNEY} 		from 'constants/user';

export default {
	loginSuccess: (user) => {
		return { 
			type: LOGIN_SUCCESS, 
			payload: {user: user} 
		}
	},
	loginFailed: (errorMessage) => {
		return {
			type: LOGIN_FAILED,
			payload: {errorMessage: errorMessage}
		}
	},
	addPlan : (plan) => {
		return {
			type: ADD_PLAN,
			payload: plan
		}
	},
	editPlan : (planId,status) => {
		return {
			type: EDIT_PLAN,
			payload : {
				planId : planId,
				status : status
			}
		}
	},
	addLocation : (planId,location) => {
		return {
			type: ADD_LOCATION,
			payload: {
				planId : planId,
				location: location
			}
		}
	},
	satrtJourney : (planId) => {
		return {
			type: START_JOURNEY,
			payload: planId
		}
	},
	removeLocation : (planId, locationId) => {
		return {
			type: REMOVE_LOCATION,
			payload: {
				planId: planId,
				locationId: locationId
			}
		}
	}
};