package com.xiaofo1022.b5235.model;

import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author yc@nbugs.com
 * @since 2016-04-20
 */
public class WeixinUser {

  private String userid;
  private String name;
  private Set<Long> department;
  private String position;
  private String mobile;
  private String gender;
  private String email;
  private String weixinid;
  private String avatar = "";
  private int status;
  private int reportCount;
  
  public WeixinUser() {}
  
  public WeixinUser(JSONObject jsonObject) {
    if (jsonObject != null) {
      this.userid = jsonObject.has("userid") ? jsonObject.getString("userid") : "";
      this.name = jsonObject.has("name") ? jsonObject.getString("name") : "";
      this.position = jsonObject.has("position") ? jsonObject.getString("position") : "";
      this.mobile = jsonObject.has("mobile") ? jsonObject.getString("mobile") : "";
      this.gender = jsonObject.has("gender") ? jsonObject.getString("gender") : "";
      this.email = jsonObject.has("email") ? jsonObject.getString("email") : "";
      this.weixinid = jsonObject.has("weixinid") ? jsonObject.getString("weixinid") : "";
      this.avatar = jsonObject.has("avatar") ? jsonObject.getString("avatar") : "";
      this.status = jsonObject.has("status") ? jsonObject.getInt("status") : -1;
      if (jsonObject.has("department")) {
        JSONArray jsonArray = jsonObject.getJSONArray("department");
        if (jsonArray != null && jsonArray.length() > 0) {
          int length = jsonArray.length();
          department = new TreeSet<>();
          for (int i = 0; i < length; i++) {
            department.add(jsonArray.getLong(i));
          }
        } else {
          department = new TreeSet<>();
        }
      }
    }
  }
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Set<Long> getDepartment() {
    return department;
  }
  public void setDepartment(Set<Long> department) {
    this.department = department;
  }
  public String getPosition() {
    return position;
  }
  public void setPosition(String position) {
    this.position = position;
  }
  public String getMobile() {
    return mobile;
  }
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
  public String getGender() {
    return gender;
  }
  public void setGender(String gender) {
    this.gender = gender;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getAvatar() {
    return avatar;
  }
  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }
  public int getStatus() {
    return status;
  }
  public void setStatus(int status) {
    this.status = status;
  }
  public String getUserid() {
    return userid;
  }
  public void setUserid(String userid) {
    this.userid = userid;
  }
  public String getWeixinid() {
    return weixinid;
  }
  public void setWeixinid(String weixinid) {
    this.weixinid = weixinid;
  }
  public int getReportCount() {
    return reportCount;
  }
  public void setReportCount(int reportCount) {
    this.reportCount = reportCount;
  }
}
