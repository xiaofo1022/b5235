/**
 * Js for Login functions
 */

var loginUser;

function dologin() {
  var code = getRequestParam('code');
  $.get('wx/login?code=' + code, function(result) {
    if (result) {
      if (result.code == 302) {
        location.assign(result.data);
      } else {
        loginUser = result.data;
        loginCallback();
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
