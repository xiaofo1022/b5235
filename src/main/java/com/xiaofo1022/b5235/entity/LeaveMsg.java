package com.xiaofo1022.b5235.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "leave_msg")
public class LeaveMsg {

  @Id
  @GeneratedValue
  private long id;
  private Date insertDatetime;
  private Date updateDatetime;
  private String msg;
  private String fromUserId;
  private String fromUserName;
  private String toUserId;
  private long toReportId;
  private int isActive = 1;
  
  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public Date getInsertDatetime() {
    return insertDatetime;
  }
  public void setInsertDatetime(Date insertDatetime) {
    this.insertDatetime = insertDatetime;
  }
  public Date getUpdateDatetime() {
    return updateDatetime;
  }
  public void setUpdateDatetime(Date updateDatetime) {
    this.updateDatetime = updateDatetime;
  }
  public String getMsg() {
    return msg;
  }
  public void setMsg(String msg) {
    this.msg = msg;
  }
  public String getFromUserId() {
    return fromUserId;
  }
  public void setFromUserId(String fromUserId) {
    this.fromUserId = fromUserId;
  }
  public String getFromUserName() {
    return fromUserName;
  }
  public void setFromUserName(String fromUserName) {
    this.fromUserName = fromUserName;
  }
  public String getToUserId() {
    return toUserId;
  }
  public void setToUserId(String toUserId) {
    this.toUserId = toUserId;
  }
  public long getToReportId() {
    return toReportId;
  }
  public void setToReportId(long toReportId) {
    this.toReportId = toReportId;
  }
  public int getIsActive() {
    return isActive;
  }
  public void setIsActive(int isActive) {
    this.isActive = isActive;
  }
}
