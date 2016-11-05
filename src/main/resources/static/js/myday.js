/**
 * Js for My day functions
 */

dologin();

function loginCallback() {
  if (loginUser) {
    $.get('report/oneday?userid=' + loginUser.userid, function(reports) {
      if (reports) {
        
      }
    });
  }
}