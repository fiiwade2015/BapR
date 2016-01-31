import { createReducer } 	from '../utils';
import { MOVE } 			from 'constants/map';
import { SELECT_LOCATION } 	from 'constants/map';
import { UNSELECT } 		from 'constants/map';
import { FILTER } 			from 'constants/map';


var filters = require('../constants/filters');


const initialState = {
	locations: [],
	filter: 'all',
	filteredLocations: [],
	currentSelected: null
}

let isLocationVisible = function(filter, locationType){
	
	if(typeof filter === 'undefined'){
		return true;
	}
	
	if(locationType === 'cluster' || filter.indexOf(locationType)>-1){
		return true;
	}

	return false;
}

let filterLocations = function(filter, locations){
	
	/*if(filter === "all"){
		return locations;
	}*/

	var result = [];
	console.log(locations.length);
	for(var i=0;i<locations.length;i++){
		var locationType = locations[i]['type'];
		//if(isLocationVisible(filter, locationType)){
			if(Array.isArray(locations[i].lat)){
				locations[i].lat  = locations[i].lat[0];
			}
			if(Array.isArray(locations[i].long)){
				locations[i].long  = locations[i].long[0];
			}
			let aux = 
			locations[i].long = parseFloat(locations[i].long);
			locations[i].lat = parseFloat(locations[i].lat);
			result.push(locations[i]);
		//}
	}

	return result;
}

export default createReducer(initialState, {
	[MOVE] : (state, action) => {
		let temp = Object.assign({}, state);
		temp.locations = action.locations;
		temp.filteredLocations = filterLocations(temp.filter, temp.locations);
		temp.currentSelected = null;

		return temp;
	},
	[SELECT_LOCATION] : (state, action) => {
		let temp = Object.assign({}, state);
		temp.currentSelected = action.locationId;
		return temp;
	},
	[UNSELECT]	:(state, action) => {
		let temp = Object.assign({},state);
		temp.currentSelected = null;
		return temp;
	},
	[FILTER]	: (state, action) => {
		let temp = Object.assign({},state);
		temp.filter = filters[action.filterName];
		temp.filteredLocations = filterLocations(temp.filter, temp.locations);
		temp.currentSelected = null;
		return temp;
	}
});