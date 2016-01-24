import React                  from 'react';
import { bindActionCreators } from 'redux';
import { connect }            from 'react-redux';
import mapAction              from 'actions/map';
import { Link }               from 'react-router';
import Filter                 from './Filter';

var ol = require('openlayers');
//var Filter = require('./Filter');


const mapStateToProps = (state) => ({
  map: state.map
});

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators(
    Object.assign({}, mapAction),
    dispatch);
}

export class FilterView extends React.Component {
  static propTypes = {
  
  }


  render () {
      return (
       <div id={this.props.id}>
        <ul>
          <Filter img="assets/info.png" width="40px" height="40px" filterName="all" icon="fa fa-list fa-2x"/>
          <Filter img="assets/info.png" width="40px" height="40px" filterName="bar" icon="fa fa-cutlery fa-2x"/>
          <Filter img="assets/info.png" width="40px" height="40px" filterName="entertainment" icon="fa fa-gamepad fa-2x"/>
        </ul>
      </div>
    );
 
    
  }

  constructor (props, context) {
    super(props, context);
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(FilterView);