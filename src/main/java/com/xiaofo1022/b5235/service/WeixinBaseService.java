package com.xiaofo1022.b5235.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaofo1022.b5235.model.WeixinUser;

@Service
public class WeixinBaseService {

  private static final String GET_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
  private static final String GET_USER_INFO_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=%s&code=%s";
  private static final String GET_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=%s&userid=%s";
  private static final String GET_JS_TICKET_URL = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=%s";
  
  @Autowired
  private HttpBaseService http;
  
  public String getAccessToken(String corpId, String corpSecret) {
    JSONObject result = http.get(GET_TOKEN_URL, corpId, corpSecret);
    if (result != null && result.has("access_token")) {
      return result.getString("access_token");
    }
    return null;
  } 
  
  public String getLoginUserId(String token, String code) {
    JSONObject result = http.get(GET_USER_INFO_URL, token, code);
    if (result != null && result.has("UserId")) {
      return result.getString("UserId");
    }
    return null;
  }
  
  public WeixinUser getUser(String token, String userId) {
    JSONObject result = http.get(GET_USER_URL, token, userId);
    if (result != null && result.has("userid")) {
      WeixinUser weixinUser = new WeixinUser(result);
      return weixinUser;
    }
    return null;
  }
  
  public String getJsTicket(String token) {
    JSONObject result = http.get(GET_JS_TICKET_URL, token);
    if (result != null && result.has("ticket")) {
      return result.getString("ticket");
    }
    return null;
  }
}
