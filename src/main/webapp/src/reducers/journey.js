import { createReducer }     from '../utils';
import { ADD,
		 REMOVE, 
		 UPDATE_STATUS,
		 ADD_LOCATION,
		 REMOVE_LOCATION} from 'constants/journey';

const initialState = {
	journeys : [],
	journeyStatus : {
		END : "end",
		ONGOING : "ongoing",
		BUILDING : "building"
	} 
};

export default createReducer(initialState, {
	[ADD] : (state,action) => {
	  	let temp = Object.assign({},state);
		temp.journeys.push(action.journey);
		return temp;
	},
	[REMOVE] : (state,action) => {
	  	let temp = Object.assign({},state);
	  	let index = 0;
		temp.journeys.map((journey, i) => {
			if(journey.id === action.journeyId){
				index = i;
			}
		});
		temp.journeys = temp.journeys.slice(0,index).concat(temp.journeys.slice(index+1);
  		return temp;
  	},
  	[UPDATE_STATUS] : (state, action) => {
		let temp = Object.assign({},state);
		temp.journeys.map(journey => {
			if(journey.id === action.journeyJd){
				journey.status = action.journeyStatus;
			}
		});
		return temp;
	},
	[ADD_LOCATION] : (state,action) => {
	  	let temp = Object.assign({},state);
		temp.journeys.map(journey => {
			if(journey.id === action.journeyId){
				journey.locations.push(action.location);
			}
		});
		return temp;
	},
	[REMOVE_LOCATION] : (state,action) => {
	  	let tempObj  = Object.assign({},state);
	  	let temp = Object.assign({},state);
		temp.journeys.map((journey, j) => {
			if(journey.id === action.journeyId){
				let index = 0;
				journey.locations.map((location,i) => {
					if(location.id === action.locationId){
						index = i;
					}
				});
				temp.journeys[j].locations = temp.journeys[j].locations.slice(0,index).concat(temp.journeys[j].locations.slice(index+1);
			}
		});
	  	return tempObj
	}
});