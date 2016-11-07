/**
 * Js for Do record
 */

+function initData() {
  initUserData();
}();

function initUserData() {
  var userid = getRequestParam('userid');
  $.get('wx/user?userid=' + userid, function(weixinUser) {
    if (weixinUser) {
      $('#header').attr('src', weixinUser.avatar);
      $('#username-info').text(weixinUser.name + '的小报告');
      initReportData();
    }
  });
}

function initReportData() {
  var userid = getRequestParam('userid');
  $.get('report/reportdays?userid=' + userid, function(reportDays) {
    if (reportDays) {
      initPage(reportDays);
    }
  });
}

function initPage(reportDays) {
  $('#report-dialog').html('');
  var reportHtml = '';
  for (var i in reportDays) {
    var reportDay = reportDays[i];
    var panelHtml = '<div class="panel panel-success">';
    panelHtml += '<div class="panel-heading"><h3 id="dialog-title" class="panel-title">' + reportDay.reportDay + '</h3></div>';
    panelHtml += '<div class="panel-body"><ul class="list-group" style="margin-bottom:0px;">';
    var reports = reportDay.reports;
    for (var j in reports) {
      var report = reports[j];
      panelHtml += '<li class="list-group-item" onclick="toRecordDetail(' + report.id + ')"><span>' + report.reportTimeInDay + ': ' + report.reportInfo + '</span></li>';
    }
    panelHtml += '</ul></div>';
    panelHtml += '</div>';
    reportHtml += panelHtml;
  }
  $('#report-dialog').html(reportHtml);
  $('#loading-bar').hide();
  $('#header-block').show();
  $('#report-dialog').show();
}

function toRecordDetail(reportid) {
  location.assign('wxmap?reportid=' + reportid);
}
