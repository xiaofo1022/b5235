package com.xiaofo1022.b5235.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xiaofo1022.b5235.model.LoginResult;
import com.xiaofo1022.b5235.model.WeixinSignature;
import com.xiaofo1022.b5235.model.WeixinToken;
import com.xiaofo1022.b5235.model.WeixinUser;
import com.xiaofo1022.b5235.service.WeixinApiService;
import com.xiaofo1022.b5235.util.CookieUtil;

@RestController
@RequestMapping("/wx")
public class WxController {

  @Autowired
  private WeixinApiService weixinApi;
  
  @RequestMapping("/signature")
  public WeixinSignature getWeixinSignature(HttpServletRequest request) throws Exception {
    WeixinToken weixinToken = weixinApi.getWeixinToken();
    String sourceUrl = request.getHeader("Referer");
    return weixinApi.getWeixinSignature(weixinToken, sourceUrl);
  }
  
  @RequestMapping("/login")
  public LoginResult login(@RequestParam(value = "code", defaultValue = "") String code, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
    if (code != null && !code.equals("")) {
      String userId = weixinApi.getLoginUserId(code);
      if (userId != null) {
        WeixinUser weixinUser = weixinApi.getLoginUser(userId);
        if (weixinUser != null) {
          CookieUtil.addCookie(CookieUtil.WX_USER_COOKIE_NAME, userId, response);
          return new LoginResult(weixinUser);
        }
      }
    } else {
      String wxUserId = CookieUtil.getCookieValue(CookieUtil.WX_USER_COOKIE_NAME, request);
      if (wxUserId != null && !wxUserId.equals("")) {
        WeixinUser weixinUser = weixinApi.getLoginUser(wxUserId);
        return new LoginResult(weixinUser);
      } else {
        return new LoginResult(302, "", weixinApi.getLoginRedirectUrl(request));
      }
    }
    return null;
  }
}
