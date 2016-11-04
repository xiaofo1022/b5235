package com.xiaofo1022.b5235.model;

public class TextMessage {

  private String touser = "";
  private String msgtype = "text";
  private int agentid;
  private Text text = new Text();
  
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
  public Text getText() {
    return text;
  }
  public void setText(Text text) {
    this.text = text;
  }
  public void setContent(String content) {
    if (this.text == null) {
      this.text = new Text();
    }
    this.text.content = content;
  }
  public String toString() {
    return "touser:" + touser + ", msgtype:" + msgtype + ", agentid:" + agentid + ", content:" + text.content;
  }
}

class Text {
  public String content = "";
}
