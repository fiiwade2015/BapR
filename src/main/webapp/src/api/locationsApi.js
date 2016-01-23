var locationsData = require('./locationsData');
var locationsApi = {

	getLocationAround: function(minX, minY, maxX, maxY){
		var result = [];
		var locations = locationsData.locations;

		for(var i = 0; i < locations.length; i++){
			if(this.between(minX, minY, maxX, maxY, locations[i])){
				result.push(locations[i]);
			}
		}
	
		return result;
	},

	between: function(minX, minY, maxX, maxY, location){
		if(location.lat >= minX && location.lat <= maxX && location.long >= minY && location.long <= maxY){
			return true;
		}

		return false;
	}	

}; 
export default locationsApi; 