import React					from 'react';
import { bindActionCreators }	from 'redux';
import { connect }           	from 'react-redux';
import userAuthAction        	from 'actions/user';


var UserApi = require( '../api/userApi');

const mapStateToProps = (state) => ({
  user : state.user,
});

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators(
    Object.assign({}, userAuthAction),
    dispatch);
}

export class LoginView extends React.Component {
	static propTypes = {
	    loginSuccess : React.PropTypes.func,
	    loginFailed : React.PropTypes.func,
	    user: React.PropTypes.object
	}
	

	componentDidMount(){

		if (navigator.geolocation) {
		    navigator.geolocation.getCurrentPosition((position) => {
		    	console.log(this);
		    	this.props.saveUserLocation(position.coords.latitude, position.coords.longitude);
		    });
		} else {
		    alert('It seems like Geolocation, which is required for this page, is not enabled in your browser. Please use a browser which supports it.');
		}

	}

	render () {
		var errorElement;
		if(this.showError) {
			errorElement = <div className="alert alert-danger"> {this.errorMessage}</div>
		}else{
			errorElement = null;
		}
		return (
			<div className='container text-center'>
				<h1>Time to travle</h1>
				<div className="form-signin">
					<div className="form-group">
			          <label className="sr-only" htmlFor="email">Emial: </label>
			          <input id="email" placeholder="Email address" className="form-control" type="text" ref= {email => { this.userEmail = email}} required/>
			        </div>
			        <div className="form-group">    
			          <label className="sr-only" htmlFor="password">Password: </label>
			          <input id="password" placeholder="Password" className="form-control" type="password" ref= {password => { this.userPassword = password}} required/>
			        </div>
			        {errorElement}
			        <button className='btn btn-lg btn-primary btn-block' onClick={this.loginUser}>Login</button>
				</div>
			</div>
		);
	}

	constructor(props, context) {
		super(props, context);
		console.log(this);
		this.loginUser = this.loginUser.bind(this);
		this.showError = false;
	}

	loginUser(){
		if(UserApi.loginUser(this.userEmail.value,this.userPassword.value)){
	      this.props.loginSuccess(UserApi.getUserData());
	      this.showError = false;
	      //redirect here
	      console.log(this);
	      this.props.history.push("/homeView");
	    }else{
	    	let errorMessage = 'Login failed, email or password are bad!';
	    	this.userEmail.value = '';
	    	this.userPassword.value = '';
	    	this.showError = true;
	    	this.errorMessage = errorMessage;
	    	this.props.loginFailed(errorMessage);
	    }
	}

/*	static willTransitionTo = function(transition, params, query, callback){
		if (UserApi.getUserData().loginStatus === true) {
            console.log('blabla');
        } else {
            callback();
        }
	}*/
}
export default connect(mapStateToProps, mapDispatchToProps)(LoginView);