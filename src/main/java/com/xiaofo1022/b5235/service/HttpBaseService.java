package com.xiaofo1022.b5235.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpBaseService {
  
  private static Logger logger = LoggerFactory.getLogger(HttpBaseService.class);
  
  @Autowired
  private RestTemplate restTemplate;
  
  public JSONObject get(String url, Object... args) {
    String requestUrl = this.getRequestUrl(url, args);
    String result = restTemplate.getForObject(requestUrl, String.class);
    if (result != null) {
      JSONObject jsonObject = new JSONObject(result);
      jsonResultErrorCheck(requestUrl, jsonObject);
      return jsonObject;
    } else {
      return new JSONObject();
    }
  }
  
  public <T> JSONObject doPost(String url, T entity, String token) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    
    HttpEntity<T> postEntity = new HttpEntity<T>(entity, headers);

    String requestUrl = this.getRequestUrl(url, token);
    String result = restTemplate.postForObject(requestUrl, postEntity, String.class);
    JSONObject jsonResult = new JSONObject(result);
    jsonResultErrorCheck(requestUrl, jsonResult);
    
    return jsonResult;
  }
  
  private String getRequestUrl(String url, Object... args) {
    return String.format(url, args);
  }
  
  private void jsonResultErrorCheck(String requestUrl, JSONObject jsonObject) {
    if (jsonObject != null && jsonObject.has("errcode")) {
      Object errorCode = jsonObject.get("errcode");
      if (errorCode != null && !errorCode.toString().equals("0")) {
        logger.error("从地址 {} 返回的数据异常: {}: {}", requestUrl, errorCode, jsonObject.get("errmsg"));
      }
    }
  }
}
