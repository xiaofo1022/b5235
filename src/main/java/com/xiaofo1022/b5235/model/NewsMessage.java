package com.xiaofo1022.b5235.model;

import java.util.ArrayList;
import java.util.List;

public class NewsMessage {
  private String touser;
  private String toparty;
  private String msgtype = "news";
  private int agentid;
  private News news = new News();
  
  public String getTouser() {
    return touser;
  }
  public void setTouser(String touser) {
    this.touser = touser;
  }
  public String getMsgtype() {
    return msgtype;
  }
  public void setMsgtype(String msgtype) {
    this.msgtype = msgtype;
  }
  public int getAgentid() {
    return agentid;
  }
  public void setAgentid(int agentid) {
    this.agentid = agentid;
  }
  public News getNews() {
    return news;
  }
  public void setNews(News news) {
    this.news = news;
  }
  public void setArticles(List<Articles> articles) {
    this.news.articles = articles;
  }
  public String getToparty() {
    return toparty;
  }
  public void setToparty(String toparty) {
    this.toparty = toparty;
  }
  public String toString() {
    return "touser:" + touser + ", msgtype:" + msgtype + ", agentid:" + agentid + ", content:multi_message";
  }
}

class News {
  public List<Articles> articles = new ArrayList<>();
}