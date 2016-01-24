import { MOVE } 			from 'constants/map';
import { SELECT_LOCATION } 	from 'constants/map';
import { UNSELECT }  from 'constants/map';
import { FILTER } 			from 'constants/map';

export default {
  move: (locations) => {
  	return { 
  		type : MOVE, 
  		payload: { 
  			locations
  		}
  	}
  },

  selectLocation: (locationId) => {
  	return { 
  		type : SELECT_LOCATION, 
  		payload: { 
  			locationId
  		}
  	}
  },

  unselect: () => {
    return{
      type: UNSELECT
    }
  },

  filter: (filterName) => {
  	return{
  		type : FILTER,
  		payload: {
  			filterName
  		}
  	}
  }

};