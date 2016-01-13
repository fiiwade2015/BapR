var userData = require('./userData');
var UserApi = {
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
	}
}; 
export default UserApi; 