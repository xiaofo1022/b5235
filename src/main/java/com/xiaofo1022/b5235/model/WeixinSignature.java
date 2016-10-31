package com.xiaofo1022.b5235.model;

/**
 * @author yc@nbugs.com
 * @since 2016-04-29
 */
public class WeixinSignature {

  private boolean debug;
  private String appId;
  private long timestamp;
  private String nonceStr;
  private String signature;
  private String[] jsApiList = new String[] { "getLocation", "openLocation" };
  
  public WeixinSignature(String appId) {
    this(appId, false);
  }
      
  public WeixinSignature(String appId, boolean debug) {
    this.appId = appId;
    this.debug = debug;
    this.timestamp = System.currentTimeMillis();
    this.nonceStr = "b5235soncestr";
  }
  
  public boolean isDebug() {
    return debug;
  }
  public void setDebug(boolean debug) {
    this.debug = debug;
  }
  public String getAppId() {
    return appId;
  }
  public void setAppId(String appId) {
    this.appId = appId;
  }
  public long getTimestamp() {
    return timestamp;
  }
  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }
  public String getNonceStr() {
    return nonceStr;
  }
  public void setNonceStr(String nonceStr) {
    this.nonceStr = nonceStr;
  }
  public String getSignature() {
    return signature;
  }
  public void setSignature(String signature) {
    this.signature = signature;
  }
  public String[] getJsApiList() {
    return jsApiList;
  }
  public void setJsApiList(String[] jsApiList) {
    this.jsApiList = jsApiList;
  }
}
