package com.xiaofo1022.b5235.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaofo1022.b5235.dao.SReportDao;
import com.xiaofo1022.b5235.entity.SReport;
import com.xiaofo1022.b5235.model.ReportDay;
import com.xiaofo1022.b5235.model.WeixinDepartment;
import com.xiaofo1022.b5235.model.WeixinUser;
import com.xiaofo1022.b5235.util.DateUtil;

@Service
public class ReportService {

  @Autowired
  private SReportDao reportDao;
  @Autowired
  private WeixinApiService weixinApi;
  
  public List<WeixinDepartment> getFriendsList(String wxUserId) {
    List<WeixinDepartment> departments = new ArrayList<>();
    WeixinUser weixinUser = weixinApi.getWeixinUser(wxUserId);
    if (weixinUser != null) {
      Set<Long> userDepartments = weixinUser.getDepartment();
      for (Long departmentId : userDepartments) {
        WeixinDepartment department = weixinApi.getWeixinDepartment(departmentId);
        if (department != null) {
          departments.add(department);
        }
      }
    }
    for (WeixinDepartment weixinDepartment : departments) {
      List<WeixinUser> weixinUsers = weixinDepartment.getWeixinUserList();
      for (WeixinUser wxuser : weixinUsers) {
        wxuser.setReportCount(getReportCount(wxuser.getUserid()));
      }
    }
    return departments;
  }
  
  private int getReportCount(String wxUserId) {
    return reportDao.findByWxUserId(wxUserId).size();
  }
  
  public List<ReportDay> getReportDays(String wxUserId) {
    List<ReportDay> reportDays = new ArrayList<>();
    List<SReport> allReports = reportDao.findByWxUserIdOrderByReportDateDesc(wxUserId);
    String dayTemp = "";
    ReportDay reportDay = null;
    for (SReport report : allReports) {
      String rday = DateUtil.getDay(report.getReportDate());
      report.setReportTimeInDay(DateUtil.getTimeInDay(report.getReportDate()));
      if (!rday.equals(dayTemp)) {
        reportDay = new ReportDay();
        reportDay.setReportDay(rday);
        reportDay.addReport(report);
        reportDays.add(reportDay);
        dayTemp = rday;
      } else {
        if (reportDay != null) {
          reportDay.addReport(report);
        }
      }
    }
    return reportDays;
  }
}
