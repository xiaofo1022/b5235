package com.xiaofo1022.b5235.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.xiaofo1022.b5235.model.WeixinUser;

@Entity
@Table(name = "sreport")
public class SReport {

  @Id
  @GeneratedValue
  private long id;
  private Date insertDatetime;
  private Date updateDatetime;
  private String wxUserId;
  private String wxUserName;
  private Date reportDate;
  private String reportLat;
  private String reportLng;
  private String reportAddress;
  private String reportInfo;
  private String reportImgServerId;
  private String gcjLat;
  private String gcjLng;
  private int isActive = 1;
  @Transient
  private WeixinUser weixinUser;
  @Transient
  private String reportImgUrl;
  @Transient
  private String reportTimeInDay;
  
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
  public String getWxUserId() {
    return wxUserId;
  }
  public void setWxUserId(String wxUserId) {
    this.wxUserId = wxUserId;
  }
  public String getWxUserName() {
    return wxUserName;
  }
  public void setWxUserName(String wxUserName) {
    this.wxUserName = wxUserName;
  }
  public Date getReportDate() {
    return reportDate;
  }
  public void setReportDate(Date reportDate) {
    this.reportDate = reportDate;
  }
  public String getReportLat() {
    return reportLat;
  }
  public void setReportLat(String reportLat) {
    this.reportLat = reportLat;
  }
  public String getReportLng() {
    return reportLng;
  }
  public void setReportLng(String reportLng) {
    this.reportLng = reportLng;
  }
  public String getReportInfo() {
    return reportInfo;
  }
  public void setReportInfo(String reportInfo) {
    this.reportInfo = reportInfo;
  }
  public int getIsActive() {
    return isActive;
  }
  public void setIsActive(int isActive) {
    this.isActive = isActive;
  }
  public String getReportAddress() {
    return reportAddress;
  }
  public void setReportAddress(String reportAddress) {
    this.reportAddress = reportAddress;
  }
  public String getGcjLat() {
    return gcjLat;
  }
  public void setGcjLat(String gcjLat) {
    this.gcjLat = gcjLat;
  }
  public String getGcjLng() {
    return gcjLng;
  }
  public void setGcjLng(String gcjLng) {
    this.gcjLng = gcjLng;
  }
  public String getReportImgServerId() {
    return reportImgServerId;
  }
  public void setReportImgServerId(String reportImgServerId) {
    this.reportImgServerId = reportImgServerId;
  }
  public WeixinUser getWeixinUser() {
    return weixinUser;
  }
  public void setWeixinUser(WeixinUser weixinUser) {
    this.weixinUser = weixinUser;
  }
  public String getReportImgUrl() {
    return reportImgUrl;
  }
  public void setReportImgUrl(String reportImgUrl) {
    this.reportImgUrl = reportImgUrl;
  }
  public String getReportTimeInDay() {
    return reportTimeInDay;
  }
  public void setReportTimeInDay(String reportTimeInDay) {
    this.reportTimeInDay = reportTimeInDay;
  }
}
