package com.xiaofo1022.b5235.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  @Bean
  public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
    PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
    poolingHttpClientConnectionManager.setMaxTotal(20);
    poolingHttpClientConnectionManager.setDefaultMaxPerRoute(999);
    return poolingHttpClientConnectionManager;
  }
  
  @Bean
  public HttpClient httpClient() {
    HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
    httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager());
    HttpClient httpClient = httpClientBuilder.build();
    return httpClient;
  }
  
  @Bean
  public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory() {
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient());
    requestFactory.setConnectTimeout(30000);
    requestFactory.setReadTimeout(30000);
    return requestFactory;
  }
  
  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory());
    return restTemplate;
  }
}
