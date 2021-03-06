package com.xiaofo1022.b5235.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaofo1022.b5235.model.Articles;
import com.xiaofo1022.b5235.model.NewsMessage;
import com.xiaofo1022.b5235.model.TextMessage;
import com.xiaofo1022.b5235.model.WeixinDepartment;
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
  private static final String DOWNLOAD_MEDIA_URL = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";
  private static final String GET_USER_SIMPLE_LIST = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=%s&department_id=%s&fetch_child=1&status=1";
  private static final String GET_DEPARTMENT_LIST = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=%s&id=%s";
  private static final String GET_USER_DETAIL_LIST = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=%s&department_id=%s&fetch_child=1&status=1";
  
  private static final Logger logger = LoggerFactory.getLogger(WeixinBaseService.class);
   
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
    textMessage.setAgentid(appProperties.getAgentid_sreport());
    textMessage.setTouser("@all");
    textMessage.setContent(content);
    http.doPost(SEND_MESSAGE_URL, textMessage, token);
  }
  
  public void sendNewsMessage(long reportId, String title, String description, String picUrl, String touser, String toparty, String token) {
    List<Articles> articlesList = new ArrayList<>();
    Articles articles = new Articles();
    articles.title = title;
    articles.description = description;
    articles.picurl = picUrl;
    articles.url = appProperties.getShowmapurl() + reportId;
    articlesList.add(articles);
    NewsMessage newsMessage = new NewsMessage();
    newsMessage.setAgentid(appProperties.getAgentid_sreport());
    newsMessage.setArticles(articlesList);
    newsMessage.setTouser(touser);
    newsMessage.setToparty(toparty);
    if (touser != null) {
      logger.info("send to user: " + touser);
    }
    if (toparty != null) {
      logger.info("send to party: " + toparty);
    }
    http.doPost(SEND_MESSAGE_URL, newsMessage, token);
  }
  
  public byte[] downloadMediaFile(String token, String mediaId) {
    String requestUrl = http.getRequestUrl(DOWNLOAD_MEDIA_URL, token, mediaId);
    return http.download(requestUrl);
  }
  
  public List<String> getUserIdsUnderDepartment(Long departmentId, String token) {
    List<String> userIds = new ArrayList<>();
    JSONObject jsonObject = http.get(GET_USER_SIMPLE_LIST, token, departmentId.toString());
    if (jsonObject != null && jsonObject.has("userlist")) {
      JSONArray jsonArray = jsonObject.getJSONArray("userlist");
      for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject userJson = jsonArray.getJSONObject(i);
        if (userJson != null && userJson.has("userid")) {
          userIds.add(userJson.getString("userid"));
        }
      }
    }
    return userIds;
  }
  
  public WeixinDepartment getDepartment(Long departmentId, String token) {
    JSONObject jsonObject = http.get(GET_DEPARTMENT_LIST, token, departmentId.toString());
    if (jsonObject != null && jsonObject.has("department")) {
      JSONObject departmentJson = jsonObject.getJSONArray("department").getJSONObject(0);
      if (departmentJson != null) {
        WeixinDepartment weixinDepartment = new WeixinDepartment(departmentJson);
        List<WeixinUser> weixinUsers = getWeixinUserList(departmentId, token);
        for (WeixinUser weixinUser : weixinUsers) {
          weixinDepartment.addWeixinUser(weixinUser);
        }
        return weixinDepartment;
      }
    }
    return null;
  }
  
  public List<WeixinUser> getWeixinUserList(Long departmentId, String token) {
    List<WeixinUser> weixinUsers = new ArrayList<>();
    JSONObject jsonObject = http.get(GET_USER_DETAIL_LIST, token, departmentId.toString());
    if (jsonObject != null && jsonObject.has("userlist")) {
      JSONArray userJsons = jsonObject.getJSONArray("userlist");
      if (userJsons != null) {
        for (int i = 0; i < userJsons.length(); i++) {
          JSONObject userJson = userJsons.getJSONObject(i);
          if (userJson != null) {
            weixinUsers.add(new WeixinUser(userJson));
          }
        }
      }
    }
    return weixinUsers;
  }
}
