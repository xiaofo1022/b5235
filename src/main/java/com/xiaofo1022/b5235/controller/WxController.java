package com.xiaofo1022.b5235.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaofo1022.b5235.model.WeixinSignature;
import com.xiaofo1022.b5235.model.WeixinToken;
import com.xiaofo1022.b5235.service.WeixinApiService;

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
}
