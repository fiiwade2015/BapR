import React                  from 'react';
import { bindActionCreators } from 'redux';
import { connect }            from 'react-redux';
import counterActions         from 'actions/counter';
import userAuthAction         from 'actions/user';
import { Link }               from 'react-router';
import Menu                   from './MenuView'

import Map                    from './MapView';
import OptionsMenu            from './LocationOptionsView';
import Filter                 from './Filter';
import FilterMenu             from './FilterView';


//var FilterMenu = require('./FilterView');
var UserApi = require( '../api/userApi');

// We define mapStateToProps where we'd normally use
// the @connect decorator so the data requirements are clear upfront, but then
// export the decorated component after the main class definition so
// the component can be tested w/ and w/o being connected.
// See: http://rackt.github.io/redux/docs/recipes/WritingTests.html
const mapStateToProps = (state) => ({
  user : state.user,
  counter : state.counter
});

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators(
    Object.assign({}, counterActions, userAuthAction),
    dispatch);
}

export class HomeView extends React.Component {
  static propTypes = {
    increment  : React.PropTypes.func,
    userAuth : React.PropTypes.func,
    user: React.PropTypes.object,
    counter  : React.PropTypes.number
  }

  render () {
    return (
      <div className="wrapper">
        <Map />
        <Menu  {...this.props} />
        <FilterMenu id="filter-menu"/>
        <OptionsMenu id="options-menu"/>
      </div>
    );
  }

  constructor (props, context) {
    super(props, context);
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(HomeView);

// to be used maybe :) <Link to='/about'>Go To About View!!</Link>