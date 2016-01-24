import React                  from 'react';
import { bindActionCreators } from 'redux';
import { connect }            from 'react-redux';
import counterActions         from 'actions/counter';
import userAction             from 'actions/user';
import { Link }               from 'react-router';
var UserApi = require( '../api/userApi');
const mapStateToProps = (state) => ({
  user : state.user,
  counter : state.counter
});

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators(
    Object.assign({}, counterActions),
    dispatch);
}

export class HealthView extends React.Component {
  static propTypes = {
    increment  : React.PropTypes.func,
    userAuth : React.PropTypes.func,
    user: React.PropTypes.object,
    counter  : React.PropTypes.number
  }

  render () {
    return (
      <div className="displayItem text-center">
        <h1>This is the HealthView</h1>
        <div className="alert alert-danger"> <i className="fa fa-exclamation-triangle"></i>Warning your located in a location which has been signaled to have a flu <strong>(influenza)</strong><i className="fa fa-exclamation-triangle"></i></div>
        <br/><hr/>
        <h3>Your health status in time!</h3>
        <img className="graph" src="http://wrobin1.volans.uberspace.de/wp-content/uploads/2014/12/apple_health_chart.png"/>
        <h3>Mood journal</h3>
        <ul className="list-group">
          <li className="list-group-item list-group-item-success">
            <span className="custom_badge"><i className="fa fa-smile-o"></i></span> I feel Awsome!
          </li>
          <li className="list-group-item list-group-item-info">
            <span className="custom_badge"><i className="fa fa-meh-o"></i></span> I feel better!
          </li>
          <li className="list-group-item list-group-item-danger">
            <span className="custom_badge"><i className="fa fa-frown-o"></i></span> I don`t feel good at all!
          </li>
        </ul>
      </div>
    );
  }

  constructor (props, context) {
    super(props, context);
    console.log(this);
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(HealthView);