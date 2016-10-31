package com.xiaofo1022.b5235.model;

import java.util.Date;

public class WeixinToken {

  private String corpId;
  private String corpSecret;
  private String accessToken;
  private Date tokenRefreshTime;
  private String jsTicket;
  private Date ticketRefreshTime;
  
  public String getCorpId() {
    return corpId;
  }
  public void setCorpId(String corpId) {
    this.corpId = corpId;
  }
  public String getCorpSecret() {
    return corpSecret;
  }
  public void setCorpSecret(String corpSecret) {
    this.corpSecret = corpSecret;
  }
  public String getAccessToken() {
    return accessToken;
  }
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
  public Date getTokenRefreshTime() {
    return tokenRefreshTime;
  }
  public void setTokenRefreshTime(Date tokenRefreshTime) {
    this.tokenRefreshTime = tokenRefreshTime;
  }
  public String getJsTicket() {
    return jsTicket;
  }
  public void setJsTicket(String jsTicket) {
    this.jsTicket = jsTicket;
  }
  public Date getTicketRefreshTime() {
    return ticketRefreshTime;
  }
  public void setTicketRefreshTime(Date ticketRefreshTime) {
    this.ticketRefreshTime = ticketRefreshTime;
  }
}
