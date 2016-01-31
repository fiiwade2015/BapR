import thunk 					from 'redux-thunk';
import userAuthAction        	from 'actions/user';
import mapAction              	from 'actions/map';
import menuAction				from 'actions/menu';

let userData = require('./userData');
let updateMap = function fetchSecretSauce(lat, long, radius) {
  return fetch('http://localhost:8080/entities?lat='+lat+'&long='+long+'&radius='+radius)
};
let viewDetails = function fetchViewDeatails(id){
	return fetch('http://localhost:8080/entities/'+id+'/details');
}

let UserApi = {
	loginUser : function(email,password) {
		if(userData.user.email === email && userData.user.password === password) {
			userData.user.loginStatus = true;
			return true;
		}
		
		userData.user.loginStatus = false;
		return false;
	},
	setErrorMessage: function(errorMessage){
		userData.user.messages.errorMessage  = errorMessage;
	},
	getErrorMessage: function(){
		return userData.user.messages.errorMessage;
	},
	getUserData: function(){
		return {id: userData.user.id,
				name: userData.user.name,
				status: userData.user.status,
				loginStatus : userData.user.loginStatus
			};
	},
	fetchTest: function(lat, long, radius){
		return function(dispatch){
			return updateMap(lat, long, radius).then(
				res => {
					res.json().then(
						json => {
							dispatch(mapAction.move(json["@graph"]));
						},
						error => {
							console.log("this s*** is not a fucking json!");
						}
					);
				},
				error => {
					console.log("s*** went down ugly as valentin!");
				}
			);
		}
	},
	fetchLocationDetails: function(locationId){
		return function(dispatch){
			return viewDetails(locationId).then(
				res => {
					res.json().then(
						json => {
							dispatch(menuAction.updateDisplayView(locationId,json));
						},
						error => {
							console.log("this is not a json format");
						}
					);
				},
				error => {
					console.log('something went really bad!');
				}
			)
		}
	}
}; 
export default UserApi; 