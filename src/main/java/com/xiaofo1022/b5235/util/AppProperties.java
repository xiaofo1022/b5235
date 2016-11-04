package com.xiaofo1022.b5235.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wx.url", locations = "classpath:app.properties")
public class AppProperties {

  private String showmap;

  public String getShowmap() {
    return showmap;
  }

  public void setShowmap(String showmap) {
    this.showmap = showmap;
  }
}
