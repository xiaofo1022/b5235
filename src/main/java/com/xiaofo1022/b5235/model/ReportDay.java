package com.xiaofo1022.b5235.model;

import java.util.ArrayList;
import java.util.List;

import com.xiaofo1022.b5235.entity.SReport;

public class ReportDay {

  private String reportDay;
  private List<SReport> reports = new ArrayList<>();
  
  public String getReportDay() {
    return reportDay;
  }
  public void setReportDay(String reportDay) {
    this.reportDay = reportDay;
  }
  public List<SReport> getReports() {
    return reports;
  }
  public void setReports(List<SReport> reports) {
    this.reports = reports;
  }
  public void addReport(SReport report) {
    reports.add(report);
  }
}
