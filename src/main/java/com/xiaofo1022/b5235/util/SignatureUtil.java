package com.xiaofo1022.b5235.util;

import java.security.MessageDigest;

/**
 * @author yc@nbugs.com
 * @since 2016-10-29
 */
public class SignatureUtil {

  public static String getSignature(String ticket, String nonce, long timestamp, String sourceUrl) throws Exception {
    String orginStr = "jsapi_ticket=" + ticket + "&noncestr=" + nonce + "&timestamp=" + timestamp + "&url=" + sourceUrl;
    MessageDigest md = MessageDigest.getInstance("SHA-1");
    md.update(orginStr.getBytes());
    byte[] digest = md.digest();
    
    StringBuffer hexstr = new StringBuffer();
    String shaHex = "";
    for (int i = 0; i < digest.length; i++) {
      shaHex = Integer.toHexString(digest[i] & 0xFF);
      if (shaHex.length() < 2) {
        hexstr.append(0);
      }
      hexstr.append(shaHex);
    }
    
    String signature = hexstr.toString();
    return signature;
  }
}
