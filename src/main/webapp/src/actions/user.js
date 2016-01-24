import { LOGIN_SUCCESS } 	from 'constants/user';
import { LOGIN_FAILED}	 	from 'constants/user';
import { ADD_PLAN }	 	 	from 'constants/user';
import { REMOVE_PLAN }		from 'constants/user';

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
	addPlan : (planId) => {
		return {
			type: ADD_PLAN,
			payload: planId
		}
	},
	removePlan : (planId) => {
		return {
			type: REMOVE_PLAN,
			payload: {
				planId: planId,
			}
		}
	}
};
