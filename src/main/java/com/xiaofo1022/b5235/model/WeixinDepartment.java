package com.xiaofo1022.b5235.model;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeixinDepartment implements Serializable {

  private long id;
  private String name;
  private long parentId;
  private int order;
  private String orguuid;
  private String orgtype;
  private List<WeixinUser> weixinUserList = new ArrayList<>();
  private Map<String, WeixinUser> weixinUserMap = new HashMap<>();
  
  public WeixinDepartment() {}
  
  public WeixinDepartment(long id, String name, long parentId, int order) {
    this.id = id;
    this.name = name;
    this.parentId = parentId;
    this.order = order;
  }
  
  public WeixinDepartment(JSONObject jsonObject) {
    if (jsonObject != null) {
      this.id = jsonObject.getLong("id");
      this.name = jsonObject.getString("name");
      this.parentId = jsonObject.getLong("parentid");
      this.order = jsonObject.getInt("order");
    }
  }
  
  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public long getParentId() {
    return parentId;
  }
  public void setParentId(long parentId) {
    this.parentId = parentId;
  }
  public int getOrder() {
    return order;
  }
  public void setOrder(int order) {
    this.order = order;
  }
  public String getOrguuid() {
    return orguuid;
  }
  public void setOrguuid(String orguuid) {
    this.orguuid = orguuid;
  }
  public String getOrgtype() {
    return orgtype;
  }
  public void setOrgtype(String orgtype) {
    this.orgtype = orgtype;
  }
  public List<WeixinUser> getWeixinUserList() {
    return weixinUserList;
  }
  public void setWeixinUserList(List<WeixinUser> weixinUserList) {
    this.weixinUserList = weixinUserList;
  }
  public void addWeixinUser(WeixinUser weixinUser) {
    if (this.weixinUserList == null) {
      this.weixinUserList = new ArrayList<>();
    }
    if (weixinUser != null && !weixinUserMap.containsKey(weixinUser.getUserid())) {
      this.weixinUserList.add(weixinUser);
      this.weixinUserMap.put(weixinUser.getUserid(), weixinUser);
    }
  }
  public Map<String, WeixinUser> getWeixinUserMap() {
    return weixinUserMap;
  }
  public void setWeixinUserMap(Map<String, WeixinUser> weixinUserMap) {
    this.weixinUserMap = weixinUserMap;
  }
}
