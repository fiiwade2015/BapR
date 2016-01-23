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

export class ListPlansView extends React.Component {
  static propTypes = {
    increment  : React.PropTypes.func,
    userAuth : React.PropTypes.func,
    user: React.PropTypes.object,
    counter  : React.PropTypes.number
  }

  render () {
    let planList = [];
    for(let i = 0; i < this.props.user.user.plans.length; i++) {
      // take care of the className;
      let className = "list-group-item ";
      let text = "View";
      if(this.props.user.user.plans[i].status === "await") {
        className += "list-group-item-info";
        text = "Edit";
      }
      if(this.props.user.user.plans[i].status === "finished") {
        className += "list-group-item-success";
      }
      if(this.props.user.user.plans[i].status === "inProgress") {
        className += "list-group-item-warning";
      }
      //add element to list;
      planList.push(
        <li className={className} key={this.props.user.user.plans[i].id}>
          <span className="badge" onClick={()=> {
            this.props.editPlan(this.props.user.user.plans[i].id,'editing');
          }}>{text}</span>{this.props.user.user.plans[i].name}
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