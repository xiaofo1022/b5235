package com.xiaofo1022.b5235.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaofo1022.b5235.entity.LeaveMsg;
import com.xiaofo1022.b5235.entity.SReport;
import com.xiaofo1022.b5235.model.WeixinSignature;
import com.xiaofo1022.b5235.model.WeixinToken;
import com.xiaofo1022.b5235.model.WeixinUser;
import com.xiaofo1022.b5235.util.AppProperties;
import com.xiaofo1022.b5235.util.FileUtil;
import com.xiaofo1022.b5235.util.SignatureUtil;

@Service
public class WeixinApiService {

  @Autowired
  private WeixinBaseService weixinBaseService;
  @Autowired
  private FileUtil fileUtil;
  @Autowired
  private AppProperties appProperties;
  
  private static final String CORP_ID = "wx66a6e31d9b7df205";
  private static final String CORP_SECRET = "gIAfHxvdQt9mWAkbwLwBfcVSznNDynr__LCVSkXjj9UFvMP2PVRR8eVmOH9iQnYG";
  private static final int TOKEN_EXPIRED_TIME = 1000 * 60 * 6 * 18;
  
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
  
  public WeixinSignature getWeixinSignature(WeixinToken weixinToken, String sourceUrl, boolean debug) throws Exception {
    WeixinSignature weixinSignature = new WeixinSignature(CORP_ID, debug);
    String signature = SignatureUtil.getSignature(weixinToken.getJsTicket(), weixinSignature.getNonceStr(), weixinSignature.getTimestamp(), sourceUrl);
    weixinSignature.setSignature(signature);
    return weixinSignature;
  }
  
  public String getWeixinUserId(String code) {
    WeixinToken weixinToken = getWeixinToken();
    if (weixinToken != null) {
      return weixinBaseService.getLoginUserId(code, weixinToken.getAccessToken());
    }
    return null;
  }
  
  public WeixinUser getWeixinUser(String userId) {
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
  
  public void sendReportMessage(SReport report) throws IOException {
    WeixinToken weixinToken = getWeixinToken();
    if (weixinToken != null) {
      String accessToken = weixinToken.getAccessToken();
      String userName = report.getWxUserName();
      String title = userName + "的小报告";
      String description = userName + "正在" + report.getReportAddress() + ": " + report.getReportInfo();
      String imgServerId = report.getReportImgServerId();
      String picUrl = null;
      if (imgServerId != null && !imgServerId.equals("")) {
        saveWxImageToServer(accessToken, imgServerId);
        picUrl = appProperties.getImageurl() + imgServerId + ".jpg";
      }
      String toparty = getSendToParty(report.getWxUserId(), accessToken);
      weixinBaseService.sendNewsMessage(report.getId(), title, description, picUrl, null, toparty, accessToken);
    }
  }
  
  private String getSendToParty(String wxUserId, String token) {
    WeixinUser weixinUser = getWeixinUser(wxUserId);
    if (weixinUser != null) {
      Set<Long> departmentIds = weixinUser.getDepartment();
      String toparty = "";
      for (Long departmentId : departmentIds) {
        toparty += (departmentId.toString() + "|");
      }
      int toPartyLength = toparty.length();
      if (toPartyLength > 1) {
        toparty = toparty.substring(0, toPartyLength - 1);
      }
      return toparty;
    }
    return "";
  }
  
  private void saveWxImageToServer(String token, String serverId) throws IOException {
    byte[] imageBytes = weixinBaseService.downloadMediaFile(token, serverId);
    String filename = serverId + ".jpg";
    fileUtil.saveImageFileToClasspath(filename, imageBytes);
  }
  
  public void sendMsgMessage(LeaveMsg leaveMsg) {
    WeixinToken weixinToken = getWeixinToken();
    if (weixinToken != null) {
      String accessToken = weixinToken.getAccessToken();
      String title = leaveMsg.getFromUserName() + "给你留言了";
      weixinBaseService.sendNewsMessage(leaveMsg.getToReportId(), title, null, null, leaveMsg.getToUserId(), null, accessToken);
    }
  }
}
