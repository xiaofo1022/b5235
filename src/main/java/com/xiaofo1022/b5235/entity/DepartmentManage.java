package com.xiaofo1022.b5235.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "department_manage")
public class DepartmentManage {

  @Id
  @GeneratedValue
  private long id;
  private Date insertDatetime;
  private Date updateDatetime;
  private long departmentId;
  private String departmentName;
  private String createUserId;
  private int isActive;
  
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
  public long getDepartmentId() {
    return departmentId;
  }
  public void setDepartmentId(long departmentId) {
    this.departmentId = departmentId;
  }
  public String getDepartmentName() {
    return departmentName;
  }
  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }
  public String getCreateUserId() {
    return createUserId;
  }
  public void setCreateUserId(String createUserId) {
    this.createUserId = createUserId;
  }
  public int getIsActive() {
    return isActive;
  }
  public void setIsActive(int isActive) {
    this.isActive = isActive;
  }
}
