package com.xiaofo1022.b5235.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiaofo1022.b5235.dao.SReportDao;
import com.xiaofo1022.b5235.entity.SReport;
import com.xiaofo1022.b5235.service.WeixinApiService;

@RestController
@RequestMapping("/report")
public class ReportController {

  @Autowired
  private SReportDao reportDao;
  @Autowired
  private WeixinApiService weixinApi;
  
  @RequestMapping(value = "/post", method = RequestMethod.POST)
  public String post(@RequestBody SReport report) {
    if (report != null) {
      Date now = new Date();
      report.setInsertDatetime(now);
      report.setUpdateDatetime(now);
      reportDao.save(report);
      weixinApi.sendNewsMessage(report);
    }
    return "ok";
  }
  
  @RequestMapping(value = "/get/{id}")
  public SReport report(@PathVariable long id) {
    return reportDao.findOne(id);
  }
}
