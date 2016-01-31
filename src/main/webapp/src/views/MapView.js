import React                  from 'react';
import { bindActionCreators } from 'redux';
import { connect }            from 'react-redux';
import counterActions         from 'actions/counter';
import mapAction              from 'actions/map';
import menuAction             from 'actions/menu';
import { Link }               from 'react-router';

var LocationsApi = require( '../api/locationsApi');
var ol = require('openlayers');
var icons = require('../constants/icons');
var UserApi = require( '../api/userApi');


const mapStateToProps = (state) => ({
  user : state.user,
  menu : state.menu,
  counter : state.counter,
  map: state.map
});

const mapDispatchToProps = (dispatch) => {
  return bindActionCreators(
    Object.assign({}, mapAction),
    dispatch);
}

export class MapView extends React.Component {
  static propTypes = {
    move: React.PropTypes.func,
    selectLocation: React.PropTypes.func,
    filter: React.PropTypes.func
  }
  static contextTypes = {
    store : React.PropTypes.object
  }
  componentDidMount(){
    var that = this;
    
    var placeLayer = new ol.layer.Vector({
      source: new ol.source.Vector({
        format: new ol.format.GeoJSON(),
        //url: "http://www.geoforall.org/locations/OSGEoLabs.json" raises
        //Cross-Origin Request Blocked: The Same Origin Policy disallows reading the remote resource at http://www.geoforall.org/locations/OSGEoLabs.json. (Reason: CORS header 'Access-Control-Allow-Origin' missing).
        url: "OSGEoLabs.json"
      })
    });

    var iconFeatures=[];
    var userLocationFeatures=[];

    var vectorSource = new ol.source.Vector({
      features: iconFeatures //add an array of features
    });
    

    this.vectorSource = vectorSource;
    var vectorLayer = new ol.layer.Vector({
      source: vectorSource
    });

    var userLocationSource = new ol.source.Vector({
      features:  userLocationFeatures//add an array of features
    });
    this.userLocationSource = userLocationSource;


    var userLocationLayer = new ol.layer.Vector({
      source: userLocationSource
    });

    var clusterSource = new ol.source.Cluster({
      distance: 40,
      source: vectorSource
    });


    var styleCache = {};
    var clusters = new ol.layer.Vector({
      source: clusterSource,
      style: function(feature, resolution) {
        var size = feature.get('features').length;
        feature.set('type', 'cluster');

        if(size > 1){
          var features = feature.get('features');
          for(var i = 0; i < size; i++){
            features[i].setStyle(null);
          }
        }
        else{
          var feature = feature.get('features')[0];
          var style = that.getStyleForFeature(feature);
          feature.setStyle(style);
        }

        var style = styleCache[size];
        if (!style && size > 1) {
          style = [new ol.style.Style({
            image: new ol.style.Circle({
              radius: 16,
              stroke: new ol.style.Stroke({
                color: '#fff'
              }),
              fill: new ol.style.Fill({
                color: '#383232'
              })
            }),
            text: new ol.style.Text({
              text: size.toString(),
              fill: new ol.style.Fill({
                color: '#fff'
              })
            })
          })];
          styleCache[size] = style;
        }
        return style;
      }
    });

    var userCurrentLocation = [that.props.user.user.currentLocation.long, that.props.user.user.currentLocation.lat];

    var userLocationMark = new ol.Feature({
      geometry: new ol.geom.Point(ol.proj.transform(userCurrentLocation, 'EPSG:4326', 'EPSG:3857')),
      type: "user" 
    });

    userLocationMark.setStyle([new ol.style.Style({
      image: new ol.style.Circle({
        radius: 10,
        stroke: new ol.style.Stroke({
          color: '#fff'
        }),
        fill: new ol.style.Fill({
          color: 'blue'
        })
      }),
      text: new ol.style.Text({
        fill: new ol.style.Fill({
          text: "user",
          color: '#fff'
        })
      })
    })]);
    userLocationSource.addFeature(userLocationMark);


    var map = new ol.Map({
      target: "map",
      controls: ol.control.defaults(),
      layers: [
        new ol.layer.Tile({
          source: new ol.source.OSM()
        }),
        vectorLayer,
        userLocationLayer,
        clusters
      ],
      view: new ol.View({
        //projection: 'EPSG:4326',
        center: new ol.geom.Point(ol.proj.transform(userCurrentLocation, 'EPSG:4326', 'EPSG:900913')).getCoordinates(),
        zoom: 16
      })
    });

    map.getControls().getArray()[0].element.className="ol-custom-zoom ol-unselectable ol-control";
    map.setSize([window.innerWidth, window.innerHeight]);

    /*
    var that = this;    
    var canvasLayer = new ol.layer.Image({
          source: new ol.source.ImageCanvas({
              canvasFunction: that.canvasFunction,
              projection: 'EPSG:3857'
          })
      });
    this.canvasLayer = canvasLayer;

      map.addLayer(canvasLayer);
    */

    
    map.getViewport().addEventListener("click", function(e) {
        map.forEachFeatureAtPixel(map.getEventPixel(e), function (feature, layer) {
          if(feature.get('type') === 'cluster'){
            map.getView().setZoom(map.getView().getZoom()+1);
          }
          else{
            //store.dispatch(MapActions.selected(feature.get('id')));
            //CALL API ?
            that.props.selectLocation(feature.get('id'));
          }
          
        });
    });
    
    map.on("moveend", function(){
      var extent = map.getView().calculateExtent(map.getSize());
      extent = ol.proj.transformExtent(extent, 'EPSG:3857', 'EPSG:4326');
      //that.props.move(LocationsApi.getLocationAround(extent[0], extent[1], extent[2], extent[3]));
      
      var center = ol.proj.transform(that.map.getView().getCenter(), 'EPSG:3857', 'EPSG:4326');
      var raza = Math.min( (center[0] - extent[0]), (center[1] - extent[1]));
      
      console.log(userCurrentLocation);
      let bulshit = UserApi.fetchTest(that.props.user.user.currentLocation.lat, that.props.user.user.currentLocation.long,2.5);
      that.context.store.dispatch(bulshit);

    });

    this.mounted = true;
    this.map = map;
  }

  getStyleForFeature(feature){
    
    var iconImg = icons[feature.get('type')] || icons['unknown'];

    var iconStyle = new ol.style.Style({
      image: new ol.style.Icon( ({
        anchor: [0.5, 1],
        anchorXUnits: 'fraction',
        anchorYUnits: 'fraction',
        opacity: 1,
        scale: 0.1,
        src: iconImg
      }))
    });
    
    /*
    var iconStyle = [new ol.style.Style({
      image: new ol.style.Circle({
        radius: 24,
        stroke: new ol.style.Stroke({
          color: '#fff'
        }),
        fill: new ol.style.Fill({
          color: 'red'
        })
      }),
      text: new ol.style.Text({
        text: feature.get('type'),
        fill: new ol.style.Fill({
          color: '#fff'
        })
      })
    })];
  */
    return iconStyle;
  }


  displayLocations(locations){
    
    this.vectorSource.clear();
    
    for(var index = 0; index < locations.length; index++){
      var coords = [locations[index].long, locations[index].lat];
      var iconFeature = new ol.Feature({
        geometry: new ol.geom.Point(ol.proj.transform(coords, 'EPSG:4326', 'EPSG:3857')),
        id: locations[index].id,
        type: locations[index].type 
      });
      iconFeature.setStyle(null);

      this.vectorSource.addFeature(iconFeature);
    }
    
  }


  render () {
    if(this.mounted){
      this.displayLocations(this.props.map['filteredLocations']);
      this.map.setSize([window.innerWidth, window.innerHeight]);
    }

    return (
      <div id="map"></div>
    );
  }

  constructor (props, context) {
    super(props, context);
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(MapView);