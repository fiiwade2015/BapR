import React                  from 'react';
import { bindActionCreators } from 'redux';
import { connect }            from 'react-redux';
import counterActions         from 'actions/counter';
import userAction             from 'actions/user';
import { Link }               from 'react-router';
var UserApi = require( '../api/userApi');
const mapStateToProps = (state) => ({
  user : state.user,
  menu : state.menu,
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
    let id = this.props.menu.displayView.displayedData["@graph"].id;
    let abstract = this.props.menu.displayView.displayedData["@graph"].abstract;
    let seeAlso = <a href={this.props.menu.displayView.displayedData["@graph"].seeAlso}>this.props.menu.displayView.displayedData["@graph"].seeAlso</a>;
    let links  = [];
    let names = [];
    let thumbnails = [];

    if(this.props.menu.displayView.displayedData["@graph"].link){
      if(Array.isArray(this.props.menu.displayView.displayedData["@graph"].link)){
        this.props.menu.displayView.displayedData["@graph"].link.map(item => links.push(<li><a href={item}>{item}</a></li>));
      }else{
        links.push(<li><a href={this.props.menu.displayView.displayedData["@graph"].link}>{this.props.menu.displayView.displayedData["@graph"].link}</a></li>);
      }
    }
    if(this.props.menu.displayView.displayedData["@graph"].name){
      if(Array.isArray(this.props.menu.displayView.displayedData["@graph"].name)) {
        this.props.menu.displayView.displayedData["@graph"].name.map(item => names.push(<li>{item}</li>));
      }else{
        names.push(<li>{this.props.menu.displayView.displayedData["@graph"].name}</li>);
      }
    }
    if(this.props.menu.displayView.displayedData["@graph"].thumbnail) {
      if(Array.isArray(this.props.menu.displayView.displayedData["@graph"].thumbnail)){
        this.props.menu.displayView.displayedData["@graph"].thumbnail.map(item => thumbnails.push(<img src={item} />));
      }else{
        thumbnails.push(<img src={this.props.menu.displayView.displayedData["@graph"].thumbnail} />);
      }
    }
    return (
      <div>
        <h2>{id}</h2>
        <br/>
        <h3>Names :</h3>
        <ul>
          {names}
        </ul>
        <hr/>
        <h3>Abstract</h3>
        <p>{abstract}</p>
        <br/>
        <h3>Links: </h3>
        <ul>
          {thumbnails}
        </ul>
        <ul>
          {links}
        </ul>
        <hr/>
        <h3>See also</h3>
        {seeAlso}
      </div>
    );
  }

  constructor (props, context) {
    super(props, context);
    console.log(this);
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(DisplayTemplateView);