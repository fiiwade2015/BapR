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
  counter : state.counter,
  journeys : state.journeys
});

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators(
    Object.assign({}, journeyAction, counterActions, userAction),
    dispatch);
}

export class ListPlansView extends React.Component {
  static propTypes = {
    increment  : React.PropTypes.func,
    userAuth : React.PropTypes.func,
    user: React.PropTypes.object,
    counter  : React.PropTypes.number
  }

  render () {
    let planList = [];
    for(let i = 0; i < this.props.journeys.journeys.length; i++) {
      // take care of the className;
      let className = "list-group-item ";
      let text = "View";
      console.log(this.props);
      if(this.props.journeys.journeys[i].status === this.props.journeys.journeyStatus.BUILDING) {
        className += "list-group-item-info";
        text = "Edit";
      }
      if(this.props.journeys.journeys[i].status === this.props.journeys.journeyStatus.END) {
        className += "list-group-item-success";
      }
      if(this.props.journeys.journeys[i].status === this.props.journeys.journeyStatus.ONGOING) {
        className += "list-group-item-warning";
      }
      //add element to list;
      planList.push(
        <li className={className} key={this.props.journeys.journeys[i].id}>
          <span className="badge" onClick={()=> {
            this.props.updatePlanView(this.props.journeys.journeys[i].id,'viewPlan');
          }}>{text}</span>{this.props.journeys.journeys[i].name}
        </li>
      );
    }
    return (
      <div>
        <h3>Current traveling plans</h3>
        <ul className="list-group">
          {planList}
        </ul>
      </div>
    );
  }

  constructor (props, context) {
    super(props, context);
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(ListPlansView);