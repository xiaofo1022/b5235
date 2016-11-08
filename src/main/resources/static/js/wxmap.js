/**
 * Js for Weixin Map
 */

var report;
var replyUserId = null;
var replyUserName = null;

dologin();

function loginCallback() {
  initWxApi();
}

function initWxApi() {
  wx.ready(function() {
    initReportData();
  });
  $.get('wx/signature', function(signature) {
    wx.config(signature);
  });
}

function initReportData() {
  var reportid = getRequestParam('reportid');
  $.get('report/get/' + reportid, function(result) {
    report = result;
    initLeaveMsgData();
  });
}

function initLeaveMsgData() {
  var reportid = report.id;
  $.get('msg/list/' + reportid, function(msgs) {
    if (msgs) {
      $('#msg-block').html('');
      var msgBlockHtml = '';
      var reportUserId = report.wxUserId;
      for (var i in msgs) {
        var lineHtml = '<p>';
        var msg = msgs[i];
        var fromUserId = msg.fromUserId;
        var toUserId = msg.toUserId;
        if (toUserId == reportUserId) {
          lineHtml += ('<span class="mr-10">' + msg.fromUserName + '说: ' + msg.msg + '</span>');
        } else {
          lineHtml += ('<span class="mr-10">' + msg.fromUserName + '回复' + msg.toUserName + ': ' + msg.msg + '</span>');
        }
        if (fromUserId == reportUserId) {
          lineHtml += ('<a onclick="replyMsg(\'' + msg.toUserId + '\', \'' + msg.toUserName + '\')">回复</a>');
        } else {
          lineHtml += ('<a onclick="replyMsg(\'' + msg.fromUserId + '\', \'' + msg.fromUserName + '\')">回复</a>');
        }
        lineHtml += '</p>';
        msgBlockHtml += lineHtml;
      }
      $('#msg-block').html(msgBlockHtml);
    }
    initDialog();
  });
}

function replyMsg(toUserId, toUserName) {
  replyUserId = toUserId;
  replyUserName = toUserName;
  $('#leave-msg').focus();
  $('#btn-msg').text('回复' + replyUserName);
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

function doLeaveMsg() {
  $('#btn-msg').attr('disabled', 'disabled');
  $('#btn-msg').text('发送中...');
  if (replyUserId != null) {
    postMsg(replyUserId, replyUserName);
  } else {
    postMsg(report.wxUserId, report.wxUserName);
  }
}

function postMsg(toUserId, toUserName) {
  var msgInfo = $('#leave-msg').val();
  if (msgInfo) {
    var leaveMsg = {};
    leaveMsg.msg = msgInfo;
    leaveMsg.fromUserId = loginUser.userid;
    leaveMsg.fromUserName = loginUser.name;
    leaveMsg.toUserId = toUserId;
    leaveMsg.toUserName = toUserName;
    leaveMsg.toReportId = report.id;
    AjaxUtil.post('msg/leave', leaveMsg, function(result) {
      location.reload();
    });
  }
}

function previewImage() {
  var imageSrc = $('#img-selected').attr('src');
  wx.previewImage({
    current: '',
    urls: [imageSrc]
  });
}
