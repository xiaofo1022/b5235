/**
 * Js for sreport
 */

var loginUser;

+function init() {
  $('#header').popover('show');
  $('.popover-title').html('2016-11-03 16:32 <br/> I am in: Binjiang Maolou');
  $('.popover-content').html('<div class="clearfix"><textarea class="form-control" rows="4" placeholder="Do?"></textarea><button class="btn btn-success fright" style="margin-top:10px;">publish</button></div>');
  $('.popover').css('top', '0px');
}();

function dologin() {
  var code = getRequestParam('code');
  $.get('wx/login?code=' + code, function(result) {
    if (result) {
      if (result.code == 302) {
        location.assign(result.data);
      } else {
        loginUser = result.data;
        $('#header').attr('src', loginUser.avatar);
      }
    }
  });
}

function getRequestParam(name) {
  var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg);
  if (r != null)
    return unescape(r[2]);
  return '';
}
