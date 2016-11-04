package com.xiaofo1022.b5235.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaofo1022.b5235.model.Articles;
import com.xiaofo1022.b5235.model.NewsMessage;
import com.xiaofo1022.b5235.model.TextMessage;
import com.xiaofo1022.b5235.model.WeixinUser;
import com.xiaofo1022.b5235.util.AppProperties;

@Service
public class WeixinBaseService {

  private static final String GET_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
  private static final String GET_USER_INFO_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=%s&code=%s";
  private static final String GET_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=%s&userid=%s";
  private static final String GET_JS_TICKET_URL = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=%s";
  private static final String GET_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=%s#wechat_redirect";
  private static final String SEND_MESSAGE_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=%s";
  private static final int SREPORT_AGENT_ID = 25;
  
  @Autowired
  private HttpBaseService http;
  @Autowired
  private AppProperties appProperties;
  
  public String getAccessToken(String corpId, String corpSecret) {
    JSONObject result = http.get(GET_TOKEN_URL, corpId, corpSecret);
    if (result != null && result.has("access_token")) {
      return result.getString("access_token");
    }
    return null;
  } 
  
  public String getLoginUserId(String code, String token) {
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
  
  public String getLoginRedirectUrl(String corpId, String redirectUri) {
    return http.getRequestUrl(GET_CODE_URL, corpId, redirectUri, corpId);
  }
  
  public void sendTextMessage(String content, String token) {
    TextMessage textMessage = new TextMessage();
    textMessage.setAgentid(SREPORT_AGENT_ID);
    textMessage.setTouser("@all");
    textMessage.setContent(content);
    http.doPost(SEND_MESSAGE_URL, textMessage, token);
  }
  
  public void sendNewsMessage(long reportId, String title, String description, String token) {
    List<Articles> articlesList = new ArrayList<>();
    Articles articles = new Articles();
    articles.title = title;
    articles.description = description;
    articles.url = appProperties.getShowmap() + reportId;
    System.out.println(articles.url);
    articlesList.add(articles);
    NewsMessage newsMessage = new NewsMessage();
    newsMessage.setAgentid(SREPORT_AGENT_ID);
    newsMessage.setArticles(articlesList);
    newsMessage.setTouser("@all");
    http.doPost(SEND_MESSAGE_URL, newsMessage, token);
  }
}
