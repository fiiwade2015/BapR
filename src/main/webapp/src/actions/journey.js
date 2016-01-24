import { ADD,
		 REMOVE, 
		 UPDATE_STATUS,
		 ADD_LOCATION,
		 REMOVE_LOCATION} from 'constants/journey';

export default {
	addJourney: (journey) => {
		return { 
			type: ADD, 
			payload: {journey: journey} 
		}
	},
	removeJourney: (journeyId) => {
		return {
			type: REMOVE,
			payload: {journeyId: journeyId}
		}
	},
	updateStatus : (journeyJd,journeyStatus) => {
		return {
			type: UPDATE_STATUS,
			payload: {
				journeyJd:journeyJd,
				journeyStatus:journeyStatus
			}
		}
	},
	addLocation : (journeyId,location) => {
		return {
			type: ADD_LOCATION,
			payload: {
				journeyId : journeyId,
				location: location
			}
		}
	},
	removeLocation : (journeyId,locationId) => {
		return {
			type: REMOVE_LOCATION,
			payload: {
				journeyId:journeyId,
				locationId:locationId
			}
		}
	},
};