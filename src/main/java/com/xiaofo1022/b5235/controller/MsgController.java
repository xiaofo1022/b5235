package com.xiaofo1022.b5235.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiaofo1022.b5235.dao.LeaveMsgDao;
import com.xiaofo1022.b5235.entity.LeaveMsg;
import com.xiaofo1022.b5235.service.WeixinApiService;

@RestController
@RequestMapping(value = "/msg")
public class MsgController {

  @Autowired
  private LeaveMsgDao leaveMsgDao;
  @Autowired
  private WeixinApiService weixinApi;
  
  @RequestMapping(value = "/leave", method = RequestMethod.POST)
  public String leave(@RequestBody LeaveMsg leaveMsg) {
    if (leaveMsg != null) {
      Date now = new Date();
      leaveMsg.setInsertDatetime(now);
      leaveMsg.setUpdateDatetime(now);
      leaveMsgDao.save(leaveMsg);
      weixinApi.sendMsgMessage(leaveMsg);
    }
    return "ok";
  }
  
  @RequestMapping("/list/{reportId}")
  public List<LeaveMsg> list(@PathVariable long reportId) {
    return leaveMsgDao.findByToReportIdOrderByInsertDatetime(reportId);
  }
}
