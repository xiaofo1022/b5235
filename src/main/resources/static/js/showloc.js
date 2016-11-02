/**
 * Js functions for Showloc
 */
function loadScript() {
  var script = document.createElement("script");  
  script.src = "http://api.map.baidu.com/api?v=2.0&ak=UCzsRGmGVrC0pyjc7GYd5osebsWEHSHc&callback=initialize";
  document.body.appendChild(script);
}

function initialize() {
  var map = new BMap.Map("map");
  map.enableScrollWheelZoom(true);
  
  var convertor = new BMap.Convertor();
  var pointArr = [];
  var ggPoint = new BMap.Point(120.136, 30.18986);
  pointArr.push(ggPoint);
  convertor.translate(pointArr, 1, 5, function (data) {
    if (data.status === 0) {
      var pt = data.points[0];
      map.centerAndZoom(pt, 18);
      var geoc = new BMap.Geocoder();
      geoc.getLocation(pt, function(rs) {
        alert('我在: ' + rs.address);
      });
    }
  });
}

window.onload = loadScript;
