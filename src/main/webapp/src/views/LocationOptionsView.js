import React                  from 'react';
import { bindActionCreators } from 'redux';
import { connect }            from 'react-redux';
import mapAction              from 'actions/map';
import menuAction             from 'actions/menu';
import journeyAction          from 'actions/journey';
import { Link }               from 'react-router';

var ol = require('openlayers');
var UserApi = require( '../api/userApi');


const mapStateToProps = (state) => ({
  menu : state.menu,
  map: state.map,
  journeys: state.journeys
});

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators(
    Object.assign({},menuAction, mapAction,journeyAction),
    dispatch);
}

export class LocationOptionsView extends React.Component {
  static propTypes = {
        changeMenu : React.PropTypes.func,
  }
  static contextTypes = {
    store : React.PropTypes.object
  }

  render () {
  
    if(this.props.map["currentSelected"]){
        return (
        <div id={this.props.id}>
            <ul>
              <li><i className="fa fa-info-circle fa-4x"  onClick={() =>{
                  this.props.updateDisplayView(this.props.map.currentSelected);
                  this.context.store.dispatch(UserApi.fetchLocationDetails(this.props.menu.displayView.displayLocationId));
              }}></i></li>
              <li><i className="fa fa-medkit fa-4x"></i></li>
              <li><i className="fa fa-plane fa-4x" onClick={() =>{
                  this.props.journeys.journeys.map(item =>{
                    if(item.id ===this.props.menu.planView.planId && item.status === this.props.journeys.journeyStatus.BUILDING){
                      this.props.addLocation(this.props.menu.planView.planId,this.props.map.currentSelected);
                    }
                  })
              }}></i></li>
              <li><i className="fa fa-globe fa-4x"></i></li>
            </ul>
          </div>
      );
    }
    else{
      return (
        <div id={this.props.id}>
          </div>
      );
    }
    
  }

  constructor (props, context) {
    super(props, context);
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(LocationOptionsView);