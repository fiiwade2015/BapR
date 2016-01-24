import { UPDATE_TOKEN, UPDATE_USER_STATUS } from 'constants/application';

export default {
	updateAppToken: (AuthToken) => {
		return { 
			type: UPDATE_TOKEN, 
			payload: {AuthToken: AuthToken} 
		}
	},
	updateAppStatus: (isLogedIn) => {
		return {
			type: UPDATE_USER_STATUS,
			payload: {isLogedIn: isLogedIn}
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
	}
};