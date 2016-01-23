import React                  from 'react';
import { bindActionCreators } from 'redux';
import { connect }            from 'react-redux';
import mapAction              from 'actions/map';
import { Link }               from 'react-router';

var ol = require('openlayers');


const mapStateToProps = (state) => ({
  map: state.map
});

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators(
    Object.assign({}, mapAction),
    dispatch);
}

export class Filter extends React.Component {
  static propTypes = {
  
  }


  render () {
    var that = this;

    //<img src={this.props.img} width={this.props.width} height={this.props.height} alt={this.props.filterName}/>

    return (
     <li className={this.props.icon} onClick = {function(){
        that.props.filter(that.props.filterName);
      }}>
      <a href="#"></a>
    </li>
    );
 
    
  }

  constructor (props, context) {
    super(props, context);
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Filter);