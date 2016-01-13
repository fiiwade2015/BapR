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

export class DisplayTemplateView extends React.Component {
  static propTypes = {
    increment  : React.PropTypes.func,
    userAuth : React.PropTypes.func,
    user: React.PropTypes.object,
    counter  : React.PropTypes.number
  }

  render () {
    return (
      <div>
        <img src="http://media-cdn.tripadvisor.com/media/photo-s/07/15/f1/8b/shibuya-crossing.jpg"/>
        <p>Tōkyō, Tokyo, uneori scris și Tokio este capitala Japoniei și cel mai mare oraș al acesteia, cu 29.489.576 locuitori, aproximativ 10% din populația țării. Orașul se situează în partea de mijloc a insulei principale a arhipelagului japonez, Honshu. Wikipedia</p>
        <p><strong>Meteo:</strong> 6 °C, vânt dinspre NE cu 14 km/h, umiditate de 58 %</p>
        <p><strong>Populaţie</strong>: 13,35 milioane (1 mai 2014)</p>
      </div>
    );
  }

  constructor (props, context) {
    super(props, context);
    console.log(this);
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(DisplayTemplateView);