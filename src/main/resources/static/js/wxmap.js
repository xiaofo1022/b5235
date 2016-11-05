/**
 * Js for Weixin Map
 */

var report;

+function initWxApi() {
  wx.ready(function() {
    initReportData();
  });
  $.get('wx/signature', function(signature) {
    wx.config(signature);
  });
}();

function initReportData() {
  var reportid = getRequestParam('reportid');
  $.get('report/get/' + reportid, function(result) {
    report = result;
    initDialog();
    openMap();
  });
}

function initDialog() {
  $('#dialog-title').text(new Date(report.reportDate).format('yyyy年M月d日 h点m分s秒'));
  $('#header').attr('src', report.weixinUser.avatar);
  $('#username-title').text(report.weixinUser.name);
  $('#report-address').text('正在: ' + report.reportAddress);
  $('#report-info').text(report.reportInfo);
  if (report.reportImgUrl) {
    $('#img-selected').attr('src', report.reportImgUrl);
  }
  $('#loading-bar').hide();
  $('#report-panel').show();
}

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
