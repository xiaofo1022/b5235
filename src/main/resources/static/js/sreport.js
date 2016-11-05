/**
 * Js for sreport
 */

var isMapLoaded = false;
var loginUser;
var currentLoc;
var currentAddress;
var currentTime;
var gcjLocation;
var currentImageServerId;

window.onload = loadScript;

function loadScript() {
  var script = document.createElement("script");  
  script.src = "http://api.map.baidu.com/api?v=2.0&ak=UCzsRGmGVrC0pyjc7GYd5osebsWEHSHc&callback=maploaded";
  document.body.appendChild(script);
}

function maploaded() {
  isMapLoaded = true;
  dologin();
}

function dologin() {
  var code = getRequestParam('code');
  $.get('wx/login?code=' + code, function(result) {
    if (result) {
      if (result.code == 302) {
        location.assign(result.data);
      } else {
        loginUser = result.data;
        initWxApi();
      }
    }
  });
}

function initWxApi() {
  wx.ready(function() {
    getGcjLocation();
  });
  $.get('wx/signature', function(signature) {
    wx.config(signature);
  });
}

function getGcjLocation() {
  wx.getLocation({
    type: 'gcj02',
    success: function (res) {
      gcjLocation = res;
      getCommonLocation();
    }
  });
}

function getCommonLocation() {
  wx.getLocation({
    type: 'wgs84',
    success: function (res) {
      currentLoc = res;
      getAddress();
    }
  });
}

function getAddress() {
  var convertor = new BMap.Convertor();
  var pointArr = [];
  var ggPoint = new BMap.Point(currentLoc.longitude, currentLoc.latitude);
  pointArr.push(ggPoint);
  convertor.translate(pointArr, 1, 5, function (data) {
    if (data.status === 0) {
      var pt = data.points[0];
      var geoc = new BMap.Geocoder();
      geoc.getLocation(pt, function(rs) {
        currentAddress = rs.address;
        initDialog();
      });
    }
  });
}

function initDialog() {
  currentTime = new Date();
  var currentTimeLabel = currentTime.format("yyyy年M月d日 h点m分s秒");
  $('#header').attr('src', loginUser.avatar);
  $('#dialog-title').html(currentTimeLabel);
  $('#report-address').val(currentAddress);
  $('#loading-bar').hide();
  $('#report-dialog').show();
}

function postReport() {
  var reportInfo = $('#report-info').val();
  if (reportInfo) {
    $('#btn-post').attr('disabled', 'disabled');
    $('#btn-post').text('发布中...');
    var report = {};
    report.wxUserId = loginUser.userid;
    report.wxUserName = loginUser.name;
    report.reportDate = currentTime;
    report.reportLat = currentLoc.latitude;
    report.reportLng = currentLoc.longitude;
    report.gcjLat = gcjLocation.latitude;
    report.gcjLng = gcjLocation.longitude;
    report.reportAddress = $('#report-address').val();
    report.reportInfo = reportInfo;
    if (currentImageServerId) {
      report.reportImgServerId = currentImageServerId;
    }
    AjaxUtil.post('report/post', report, function(result) {
      wx.closeWindow();
    });
  } else {
    swal('说点什么吧~');
    $('#report-info').focus();
  }
}

function getRequestParam(name) {
  var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg);
  if (r != null)
    return unescape(r[2]);
  return '';
}

function selectImage() {
  wx.chooseImage({
    count: 1,
    sizeType: ['original', 'compressed'],
    sourceType: ['album', 'camera'],
    success: function (res) {
      var localIds = res.localIds;
      var firstImageId = localIds[0];
      if (firstImageId) {
        $('#img-selected').attr('src', firstImageId);
        $('#img-selected').show();
        uploadImage(firstImageId);
      }
    }
  });
}

function uploadImage(firstImageId) {
  wx.uploadImage({
    localId: firstImageId,
    isShowProgressTips: 1,
    success: function (res) {
      currentImageServerId = res.serverId;
    }
  });
}
