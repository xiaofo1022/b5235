/**
 * Js for sreport
 */

var isMapLoaded = false;
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

function loginCallback() {
  initWxApi();
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
  if (isUserHasMultiGroup()) {
    initGroupBlock();
  } else {
    $('#loading-bar').hide();
    $('#report-dialog').show();
  }
}

function isUserHasMultiGroup() {
  var userDepartments = loginUser.department;
  if (userDepartments.length > 1) {
    return true;
  }
  return false;
}

function initGroupBlock() {
  $.get('wx/departments?userid=' + loginUser.userid, function(departments) {
    if (departments) {
      $('#group-block').html('');
      var groupHtml = '';
      for (var i in departments) {
        var department = departments[i];
        var btnHtml = '<button id="' + department.id + '" type="button" class="btn btn-info btn-md btn-send-group fright" style="margin-left:5px;" onclick="changeGroupButtonStatus(this)">' + department.name + '<span class="glyphicon glyphicon-ok"></span></button>';
        groupHtml += btnHtml;
      }
      $('#group-block').html(groupHtml);
      $('#group-block').show();
    }
    $('#report-dialog').show();
    $('#loading-bar').hide();
  });
}

function changeGroupButtonStatus(groupButton) {
  if (isGroupButtonSelected(groupButton)) {
    var spanOk = $(groupButton).find('span');
    spanOk.remove();
  } else {
    $(groupButton).append('<span class="glyphicon glyphicon-ok"></span> ');
  }
}

function isGroupButtonSelected(groupButton) {
  var spanOk = $(groupButton).find('span');
  if (spanOk.length > 0) {
    return true;
  }
  return false;
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
    if (isUserHasMultiGroup()) {
      var toparty = getSelectedParty();
      if (toparty == '') {
        swal('至少选择一个分组哦');
        $('#btn-post').removeAttr('disabled');
        $('#btn-post').text('发布');
        return;
      } else {
        report.toparty = toparty;
      }
    }
    AjaxUtil.post('report/post', report, function(result) {
      wx.closeWindow();
    });
  } else {
    swal('说点什么吧~');
    $('#report-info').focus();
  }
}

function getSelectedParty() {
  var groupButtons = $('#group-block').find('button');
  var buttonCount = groupButtons.length;
  var toparty = '';
  for (var i = 0; i < buttonCount; i++) {
    var groupButton = groupButtons[i];
    if (isGroupButtonSelected(groupButton)) {
      var partyId = groupButton.id;
      toparty += (partyId + '|');
    }
  }
  if (toparty.length > 1) {
    toparty = toparty.substring(0, toparty.length - 1);
  }
  return toparty;
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
