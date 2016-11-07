/**
 * Js for My day functions
 */

dologin();

function loginCallback() {
  if (loginUser) {
    initData(loginUser.userid);
  }
}

function initData(userid) {
  $.get('report/whattheydo?userid=' + userid, function(departments) {
    if (departments) {
      initPage(departments)
    }
  });
}

function initPage(departments) {
  $('#report-dialog').html('');
  var reportHtml = '';
  for (var i in departments) {
    var department = departments[i];
    var panelHtml = '<div class="panel panel-success">';
    panelHtml += '<div class="panel-heading"><h3 id="dialog-title" class="panel-title">' + department.name + '</h3></div>';
    panelHtml += '<div class="panel-body"><ul class="list-group" style="margin-bottom:0px;">';
    var weixinUsers = department.weixinUserList;
    for (var j in weixinUsers) {
      var weixinUser = weixinUsers[j];
      panelHtml += '<li class="list-group-item" onclick="toDoRecord(\'' + weixinUser.userid + '\')"><span class="badge">' + weixinUser.reportCount + '个小报告</span><img class="minheader" src="' + weixinUser.avatar + '"/><span>' + weixinUser.name + '</span></li>';
    }
    panelHtml += '</ul></div>';
    panelHtml += '</div>';
    reportHtml += panelHtml;
  }
  $('#report-dialog').html(reportHtml);
  $('#loading-bar').hide();
  $('#report-dialog').show();
}

function toDoRecord(userId) {
  location.assign('dorecord?userid=' + userId);
}
