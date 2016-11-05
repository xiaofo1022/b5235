/**
 * Js for Weixin Map
 */

var report;
var loginUser;
var replyUserId;

+function dologin() {
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
}();

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
      for (var i in msgs) {
        var lineHtml = '<p>';
        var msg = msgs[i];
        lineHtml += ('<span class="mr-10">' + msg.fromUserName + '说: ' + msg.msg + '</span>');
        lineHtml += ('<a onclick="replyMsg(\'' + msg.fromUserId + '\')">回复</a>');
        lineHtml += '</p>';
        msgBlockHtml += lineHtml;
      }
      $('#msg-block').html(msgBlockHtml);
    }
    initDialog();
  });
}

function replyMsg(toUserId) {
  replyUserId = toUserId;
  $('#leave-msg').focus();
  $('#btn-msg').text('回复');
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
  $('#btn-msg').text('发送中');
  if (replyUserId) {
    postMsg(replyUserId);
  } else {
    postMsg(report.wxUserId);
  }
}

function postMsg(toUserId) {
  var msgInfo = $('#leave-msg').val();
  if (msgInfo) {
    var leaveMsg = {};
    leaveMsg.msg = msgInfo;
    leaveMsg.fromUserId = loginUser.userid;
    leaveMsg.fromUserName = loginUser.name;
    leaveMsg.toUserId = toUserId;
    leaveMsg.toReportId = report.id;
    AjaxUtil.post('msg/leave', leaveMsg, function(result) {
      location.reload();
    });
  }
}
