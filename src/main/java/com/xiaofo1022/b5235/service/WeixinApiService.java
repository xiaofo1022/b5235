package com.xiaofo1022.b5235.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaofo1022.b5235.model.WeixinSignature;
import com.xiaofo1022.b5235.model.WeixinToken;
import com.xiaofo1022.b5235.model.WeixinUser;
import com.xiaofo1022.b5235.util.SignatureUtil;

@Service
public class WeixinApiService {

  @Autowired
  private WeixinBaseService weixinBaseService;
  
  private static final String CORP_ID = "wx66a6e31d9b7df205";
  private static final String CORP_SECRET = "gIAfHxvdQt9mWAkbwLwBfcVSznNDynr__LCVSkXjj9UFvMP2PVRR8eVmOH9iQnYG";
  private static final int TOKEN_EXPIRED_TIME = 1000 * 60 * 60 * 2;
  
  private static WeixinToken tokenCache;
  
  public WeixinToken getWeixinToken() {
    if (tokenCache == null) {
      tokenCache = createWeixinToken();
    } else {
      if (System.currentTimeMillis() - tokenCache.getTokenRefreshTime().getTime() > TOKEN_EXPIRED_TIME) {
        tokenCache = createWeixinToken();
      }
    }
    return tokenCache;
  }
  
  private WeixinToken createWeixinToken() {
    String token = weixinBaseService.getAccessToken(CORP_ID, CORP_SECRET);
    Date now = new Date();
    WeixinToken weixinToken = new WeixinToken();
    weixinToken.setAccessToken(token);
    weixinToken.setTokenRefreshTime(now);
    String jsTicket = weixinBaseService.getJsTicket(token);
    weixinToken.setJsTicket(jsTicket);
    weixinToken.setTicketRefreshTime(now);
    return weixinToken;
  }
  
  public WeixinSignature getWeixinSignature(WeixinToken weixinToken, String sourceUrl) throws Exception {
    WeixinSignature weixinSignature = new WeixinSignature(CORP_ID, true);
    String signature = SignatureUtil.getSignature(weixinToken.getJsTicket(), weixinSignature.getNonceStr(), weixinSignature.getTimestamp(), sourceUrl);
    weixinSignature.setSignature(signature);
    return weixinSignature;
  }
  
  public String getLoginUserId(String code) {
    WeixinToken weixinToken = getWeixinToken();
    if (weixinToken != null) {
      return weixinBaseService.getLoginUserId(code, weixinToken.getAccessToken());
    }
    return null;
  }
  
  public WeixinUser getLoginUser(String userId) {
    WeixinToken weixinToken = getWeixinToken();
    if (weixinToken != null) {
      return weixinBaseService.getUser(weixinToken.getAccessToken(), userId);
    }
    return null;
  }
  
  public String getLoginRedirectUrl(HttpServletRequest request) throws UnsupportedEncodingException {
    String originUrl = request.getHeader("Referer");
    String encodeUrl = URLEncoder.encode(originUrl, "utf-8");
    return weixinBaseService.getLoginRedirectUrl(CORP_ID, encodeUrl);
  }
}
