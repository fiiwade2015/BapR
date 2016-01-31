import React                  from 'react';
import { bindActionCreators } from 'redux';
import { connect }            from 'react-redux';
import counterActions         from 'actions/counter';
import userAction             from 'actions/user';
import journeyAction          from 'actions/journey';
import { Link }               from 'react-router';

var UserApi = require( '../api/userApi');
const mapStateToProps = (state) => ({
  user : state.user,
  journeys : state.journeys,
  counter : state.counter
});

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators(
    Object.assign({}, counterActions),
    dispatch);
}

export class ListLocationView extends React.Component {
  static propTypes = {
    increment  : React.PropTypes.func,
    userAuth : React.PropTypes.func,
    user: React.PropTypes.object,
    counter  : React.PropTypes.number
  }

  render () {
    let planList = [];
    let locationList = [];
    let className = "list-group-item ";
    let text = "View";
    let canEdidPlan = true;
    let canEditLocation = false;
    this.props.journeys.journeys.map(item => {
      if(item.id === this.props.menu.planView.planId) {
        if(item.status === "building") {
          className += "list-group-item-info";
          text = "Edit";
          canEdidPlan = false;
        }
        if(item.status === "finished") {
          className += "list-group-item-success";
        }
        if(item.status === "inProgress") {
          className += "list-group-item-warning";
          canEditLocation = true;
        }
        //to display the current plan we are working on
        planList.push(
          <li className={className} key={item.id}>
              {item.name}
          </li>
        );

        // to display the list of location
        item.locations.map(location => {
          locationList.push(
            <li className={className} key={location.id}>
              <span className="badge" onClick={() => {
                this.props.changeMenu('display',location.id);
              }}>View</span>
              {canEditLocation? <span className="badge">Visited</span> : null}
              {location.name}
            </li>
          );
        });
      }
    });

    return (
      <div className="displayItem">
        <p>{text} travle plan</p>
        <ul className="list-group">
          {planList}
        </ul>
        <div>
          <div className="form-group">
            <label className="sr-only" htmlFor="addLocation">Lication Name: </label>
            <input disabled={canEdidPlan} id="addLocation" placeholder="Lication Name" className="form-control" type="text" ref= {location => { this.locationElement = location}} required/>
          </div>
          <p>Add Locations to Journey! (from map)</p>
          <h3>Traveling location</h3>
          <ul className="list-group">
            {locationList}
          </ul>
          <button className='btn btn-default btn-block' onClick={() => {
            this.props.updatePlanView(null,'viewPlans');
            }}>Back to plan list!</button>

          <button disabled={canEdidPlan} className='btn btn-success btn-block' onClick={() => {
            this.props.satrtJourney(this.props.user.editPlans.planId);
            }}>Start journey!</button>
        </div>
      </div>
    );
  }

  constructor (props, context) {
    super(props, context);
    this.currentLocationId = 0;
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(ListLocationView);