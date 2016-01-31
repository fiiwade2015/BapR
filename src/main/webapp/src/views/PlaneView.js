import React                  from 'react';
import { bindActionCreators } from 'redux';
import { connect }            from 'react-redux';
import counterActions         from 'actions/counter';
import userAction             from 'actions/user';
import journeyAction          from 'actions/journey';
import { Link }               from 'react-router';
import ListPlansView          from './ListPlansView';
import ListLocationView       from './ListLocationView';

var UserApi = require( '../api/userApi');
const mapStateToProps = (state) => ({
  user : state.user,
  menu : state.menu,
  journeys : state.journeys,
  counter : state.counter
});

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators(
    Object.assign({}, userAction,counterActions,journeyAction),
    dispatch);
}

export class PlaneView extends React.Component {
  static propTypes = {
    increment  : React.PropTypes.func,
    userAuth : React.PropTypes.func,
    user: React.PropTypes.object,
    counter  : React.PropTypes.number
  }

  render () {
    let disableStatus = false; 
    if(this.props.menu.planView.statusView !== "viewPlans"){
      disableStatus = true;
    }
    return (
      <div className="displayItem text-center">
        <h1><i className="fa fa-globe"></i>This is the PlaneView</h1>
        
          <div className="form-group">
            <label className="sr-only" htmlFor="addPlan">Emial: </label>
            <input disabled={disableStatus} id="addPlan" placeholder="Plan name" className="form-control" type="text" ref= {plan => { this.planElement = plan}} required/>
          </div>
          <button disabled={disableStatus} className='btn btn-default btn-block' onClick={() => {
            this.currentPlanId += 1;
            this.currentPlanId += 1;
            let currentDate = Date();
            this.props.addJourney({
              id: this.currentPlanId,
              name: this.planElement.value,
              locations: [],
              status: this.props.journeys.journeyStatus.BUILDING,
              createdAt : currentDate
            });
            this.props.addPlan(this.currentPlanId);
          }}>New Plan</button>
        <div>
          {this.props.menu.planView.statusView === "viewPlans"? <ListPlansView {...this.props}/> : <ListLocationView {...this.props}/>}
        </div>
      </div>
    );
  }

  constructor (props, context) {
    super(props, context);
    console.log(this);
    this.currentPlanId = 0;
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(PlaneView);