/**
 * Js for Weixin Map
 */

var report;

+function initWxApi() {
  var reportid = getRequestParam('reportid');
  wx.ready(function() {
    $.get('report/get/' + reportid, function(result) {
      report = result;
      $('#loading-bar').hide();
      $('#btn-open-map').show();
      openMap();
    });
  });
  $.get('wx/signature', function(signature) {
    wx.config(signature);
  });
}();

function openMap() {
  wx.openLocation({
    latitude: parseFloat(report.gcjLat),
    longitude: parseFloat(report.gcjLng),
    name: report.wxUserName + '在此',
    address: report.reportInfo,
    scale: 14,
    infoUrl: ''
  });
}

function getRequestParam(name) {
  var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg);
  if (r != null)
    return unescape(r[2]);
  return '';
}
