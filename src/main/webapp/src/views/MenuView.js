import React                  from 'react';
import { bindActionCreators } from 'redux';
import { connect }            from 'react-redux';
import counterActions         from 'actions/counter';
import userAction             from 'actions/user';
import menuAction             from 'actions/menu';
import { Link }               from 'react-router';

import DisplayView            from './DisplayView';
import HealthView             from './HealthView';
import PlaneView              from './PlaneView';
import SettingsView           from './SettingsView'


var UserApi = require( '../api/userApi');
const mapStateToProps = (state) => ({
  user : state.user,
  menu : state.menu,
  counter : state.counter
});

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators(
    Object.assign({}, counterActions,menuAction),
    dispatch);
}

export class MenuView extends React.Component {
  static propTypes = {
    increment  : React.PropTypes.func,
    counter  : React.PropTypes.number,
    changeMenu : React.PropTypes.func
  }
//
  render () {
      let menuElements = [];
      let displayItemClass = "itemsMenu ";
      let displayItem;
      let hasHide = true;

      for(let item in this.props.menu){
        if(this.props.menu.hasOwnProperty(item)){
          //push elements into menuelements to be draw
          menuElements.push(
            <li key={this.props.menu[item].id} onClick={() => {
              this.props.changeMenu(this.props.menu[item].iconName);
            }} className="itemMenu">
              <i className={this.props.menu[item].className}></i>
            </li>);

          if(this.props.menu[item].visible){
            hasHide = false;
            displayItem = this.props.menu[item].iconName;
          }
        }
      }

      if(hasHide) {
        displayItemClass += "hide";
      }

    return (
      <div className="Menu">
        <div className={displayItemClass}>
          { displayItem === "display"? <DisplayView {...this.props}/> : null}
          { displayItem === "health"? <HealthView {...this.props}/> : null}
          { displayItem === "plan"? <PlaneView {...this.props}/> : null}
          { displayItem === "setting"? <SettingsView {...this.props}/> : null}
        </div>
        <ul className="buttonsMenu">
          {menuElements}
        </ul>
      </div>
    );
  }

  constructor (props, context) {
    super(props, context);
    this.liElementClicked = this.liElementClicked.bind(this);
  }

  liElementClicked(){

  }
}

export default connect(mapStateToProps, mapDispatchToProps)(MenuView);