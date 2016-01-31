import React                  from 'react';
import { bindActionCreators } from 'redux';
import { connect }            from 'react-redux';
import counterActions         from 'actions/counter';
import userAction             from 'actions/user';
import { Link }               from 'react-router';
import DisplayTempleateView   from './DisplayTemplateView'
var UserApi = require( '../api/userApi');
const mapStateToProps = (state) => ({
  user : state.user,
  map : state.map,
  menu: state.menu,
  counter : state.counter
});

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators(
    Object.assign({}, counterActions),
    dispatch);
}

export class DisplayView extends React.Component {
  static propTypes = {
    increment  : React.PropTypes.func,
    userAuth : React.PropTypes.func,
    user: React.PropTypes.object,
    counter  : React.PropTypes.number
  }

  render () {
    let locationName = null;
    if(this.props.menu.displayView.displayLocationId){
      this.props.map.locations.map(item => {
        if(item.id === this.props.menu.displayView.displayLocationId){
          locationName = item.name;
        }
      })
    }
    return (
      <div className="displayItem text-center">
        <h1>This is the DisplayView</h1>
        {locationName? <h3>{locationName}</h3> : <p>Please select a location first!</p>}
        <br/>
        <hr/>
        {this.props.menu.displayView.displayedData? <DisplayTempleateView {...this.props} /> : null}
      </div>
    );
  }

  constructor (props, context) {
    super(props, context);
    console.log(this);
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(DisplayView);