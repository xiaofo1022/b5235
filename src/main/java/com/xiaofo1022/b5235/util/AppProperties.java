package com.xiaofo1022.b5235.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app", locations = "classpath:app.properties")
public class AppProperties {

  private String showmapurl;
  private String imageurl;
  private int agentid_sreport;
  
  public String getShowmapurl() {
    return showmapurl;
  }
  public void setShowmapurl(String showmapurl) {
    this.showmapurl = showmapurl;
  }
  public String getImageurl() {
    return imageurl;
  }
  public void setImageurl(String imageurl) {
    this.imageurl = imageurl;
  }
  public int getAgentid_sreport() {
    return agentid_sreport;
  }
  public void setAgentid_sreport(int agentid_sreport) {
    this.agentid_sreport = agentid_sreport;
  }
}
